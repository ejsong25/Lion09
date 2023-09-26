
var modalOpenBtn = document.querySelector('.modal-open-btn');
var modal = document.querySelector('.modal');
var modalOverlay = document.querySelector('.modal-overlay');
var modalCloseX = document.querySelector('.modal-close-x');
var modalCloseBtn = document.querySelector('.modal-close-btn');

function getScrollbarWidth() {
    return window.innerWidth - document.documentElement.clientWidth;
}

function openModal() {
    modal.classList.add('modal-active');
    document.body.style.paddingRight = getScrollbarWidth()+'px';
    document.body.classList.add('modal-open');
}

function closeModal() {
    modal.classList.remove('modal-active');
    document.body.removeAttribute("style");
    document.body.classList.remove('modal-open');
}

modalOpenBtn.addEventListener('click', openModal);
modalOverlay.addEventListener('click', closeModal);
modalCloseBtn.addEventListener('click', closeModal);
modalCloseX.addEventListener('click', closeModal);
document.addEventListener('keyup', function (e) {
    if (e.keyCode === 27) {
        closeModal();
    }
});     