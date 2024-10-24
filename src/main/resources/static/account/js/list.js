let userName;

function getAcc(param) {
  userName = param;
}

$("#confirmDel").click(function () {
  window.location.href = "delete?userName=" + userName;
});
