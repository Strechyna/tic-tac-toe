const basePath = 'http://localhost:8080/battles';
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/websocket'
});

var battleNumber = 0;
var selectedRow;
var currentBattle;
var isBattleOwner;
var battleStatus;
var battleResultMessage;


const playerId = this.crypto.randomUUID();
const BOARD_SIZE = 3;

const alert = (message, type) => {
    const wrapper = document.createElement('div')
    wrapper.innerHTML = [
        `<div class="alert alert-${type} alert-dismissible" role="alert">`,
        `   <div>${message}</div>`,
        '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
        '</div>'
    ].join('');

    $("#alertPlaceholder").append(wrapper);
}

async function fetchWithOptions(url, method, request) {
    return fetch(url, {
        method: method,
        body: request
    }).then(response => response.json());
}

async function post(url, request) {
    return fetchWithOptions(url, 'POST', request);
}

async function get(url) {
    return fetchWithOptions(url, 'GET', null);
}

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe(`/topic/battle/${currentBattle.id}`, (battle) => {
        console.log(battle.body);
        showBattleStatus(JSON.parse(battle.body));
    });
    stompClient.subscribe("/user/queue/errors", (message) => alert(`Error: ${message.body}`, 'danger'));
    if (!isBattleOwner) {
        stompClient.publish({
            destination: `/app/battle/${currentBattle.id}/join`,
            body: playerId
        });
    }
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function createBattle() {
    post(basePath, playerId).then(battle => {
        console.log(battle.id);
        currentBattle = battle;
        isBattleOwner = true;
        joinCurrentBattle();
        alert('Please wait second player', 'warning');
    });
}

function findBattles() {
    $("#battles tbody tr").remove();
    battleNumber = 0;
    get(basePath).then(response => response.forEach(appendBattle));
    $("#battles").show();
}

function selectRow(row) {
    if (selectedRow) {
        selectedRow.removeClass('table-active');
    }
    selectedRow = row;
    selectedRow.addClass('table-active');
    $("#join").prop("disabled", false);
    setCurrentBattle();
}

function setCurrentBattle() {
    currentBattle = {
        'id': selectedRow.find("td:eq(0)").text()
    };
    console.log(JSON.stringify(currentBattle));
}

function makeMove(cellId) {
    stompClient.publish({
        destination: `/app/battle/${currentBattle.id}/move`,
        body: JSON.stringify({
            'cell': {
                'rowIndex': cellId.charAt(4),
                'columnIndex': cellId.charAt(5)
            },
            'playerId': playerId
        })
    });
}

function interrupt() {
    stompClient.publish({
        destination: `/app/battle/${currentBattle.id}/interrupt`
    });
}

function appendBattle(battle) {
    $('#battles tbody').append(`<tr><th scope="row">${++battleNumber}</th><td>${battle.id}</td><td></td></tr>`);
}

function joinCurrentBattle() {
    selectedRow = null;
    stompClient.activate();
    ableOrDisableButtons(true);
    showBattleBoard();
}

function showLeaveCurrentBattle() {
    toggleModal('#leaveBattleModal');
}

function showCloseBattleModal(message, modalHeaderClass) {
    setModalHaderClass('#closeBattleModalHeader', modalHeaderClass);
    $('#closeBattleModal .modal-body').text(message);
    toggleModal('#closeBattleModal');
}

function setModalHaderClass(modalHeaderId, modalHeaderClass) {
    $(modalHeaderId).removeClass();
    $(modalHeaderId).addClass('modal-header');
    $(modalHeaderId).addClass(modalHeaderClass);
}

function toggleModal(modalId) {
    const modal = new bootstrap.Modal(modalId, {
        keyboard: true
    });
    modal.toggle();
}

function clouseCurrentBattle() {
    stompClient.deactivate();
    console.log("Disconnected");
    ableOrDisableButtons(false);
    hideBattleBoard();
    clearCurrentBattleInfo();
}

function clearCurrentBattleInfo() {
    currentBattle = null;
    isBattleOwner = null;
    battleStatus = null;
}

function showBattleStatus(battle) {
    updateBattleBoard(battle.board.board);
    onResultIsKnown(battle.result);
    onBattleStatusChange(battle.status);
}

function onResultIsKnown(result) {
    if (result) {
        setResultMessage(result);
        alert(battleResultMessage, 'success');
    }
}

function setResultMessage(result) {
    switch (result) {
        case 'FIRST_PLAYER_WON':
            battleResultMessage = 'First player won';
            break;
        case 'SECOND_PLAYER_WON':
            battleResultMessage = 'Second player won';
            break;
        case 'DRAW':
            battleResultMessage = 'Draw';
    }
}

function onBattleStatusChange(status) {
    if (battleStatus !== status) {
        battleStatus = status;
        switch (battleStatus) {
            case 'IN_PROGRESS':
                if (isBattleOwner) {
                    alert('Second player joined to battle', 'info');
                }
                alert('Battle began', 'info');
                break;
            case 'FINISHED':
                setTimeout(() => showCloseBattleModal(battleResultMessage, 'modal-header-success'), 1000);
                break;
            case 'INTERRUPTED':
                showCloseBattleModal('Battle was interrupted', 'modal-header-danger');
        }
    }
}

function updateBattleBoard(board) {
    for (var rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
        for (var columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
            if (board[rowIndex][columnIndex]) {
                $(`#cell${rowIndex}${columnIndex}`).text(board[rowIndex][columnIndex]);
            }
        }
    }
}

function ableOrDisableButtons(isJoin) {
    $("#create").prop("disabled", isJoin);
    $("#find").prop("disabled", isJoin);
    $("#join").prop("disabled", !isJoin);
    $("#leave").prop("disabled", !isJoin);
}

function showBattleBoard() {
    $("#button-group").hide();
    $("#leave").show();
    $("#board").show();
    $("#battles").hide();
}

function hideBattleBoard() {
    $("#button-group").show();
    $("#leave").hide();
    $("#board").hide();
    $("#board td").text('');
    $("#alertPlaceholder div").remove();
}

$(function () {
    $('#create').click(() => createBattle());
    $('#find').click(() => findBattles());
    $('#leave').click(() => showLeaveCurrentBattle());
    $('#yesLeaveBattle').click(() => interrupt());
    $('#okCloseBattle').click(() => clouseCurrentBattle());
    $('#join').click(() => joinCurrentBattle());
    $('#battles tbody').on('click', 'tr', function () {
        selectRow($(this));
    });
    $('#board td').click(function () {
        makeMove(this.id);
    });
});
