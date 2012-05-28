var pagesOverall = undefined;
var pageNrTemp = 1;

function main() {
  var a4Width = 210;
  var a4Height = 297;

  var pixelWidth = document.getElementById('top').offsetWidth;
  var pixelHeight = document.getElementById('top').offsetHeight;

  var millimeterPerPixel = a4Width / pixelWidth;
  var entireDocumentHeightInMillimeter = millimeterPerPixel * pixelHeight;

  pagesOverall = Math.ceil(entireDocumentHeightInMillimeter / a4Height);

  var all = document.getElementsByTagName("*");

  var sumHeight = 0;
  var lastSumHeight = undefined;
  var last_i = undefined;

  for (var i=0; i < all.length; i++) {
    if (all[i].className === "row") {
      var mm = all[i].clientHeight * millimeterPerPixel;
      lastSumHeight = sumHeight;
      sumHeight += mm;// all[i].clientHeight;
      //console.log(i + " " + sumHeight);
      if (sumHeight > a4Height) {
        console.log(a4Height - lastSumHeight);
        splitAt(last_i, a4Height - lastSumHeight);
        i = i + 7;  // footer template elements + 1
        sumHeight = sumHeight - lastSumHeight; // delta
      }
      last_i = i;
    }
  }
  splitAt(last_i, a4Height - sumHeight);
  //console.log(sumHeight);

}

/*
function getStringFromURL(url, callback) {
  var getter = function() {
    var that = this;
    this.result = "undef";
    this.func = function(response) {
      that.result = response;
      callback(response);
    };
  };
  var x = new getter();
  $.get(url, x.func);
  return x.result;
}*/

function footerTemplate(height) {
  // height = 53 pixel bei MIR, ~14.04mm (without the additional height)
  height = height - 14;
  return (
    '<div class="row">' +
    '  <div class="col1" style="width: 210mm; height: ' + height + 'mm">&nbsp;</div>' +
    '  <div align="center" class="col10">' +
    '    <p>Seite ' + pageNrTemp++ + ' von ' + pagesOverall + '</p>' +
    '  </div>' +
    '  <div class="col1">&nbsp;</div>' +
    '  <div class="row-end">&nbsp;</div>' +
    '</div>'
  );
}

function splitAt(i, height) {
  var all = document.getElementsByTagName("*");
  var wrapper = document.createElement("div");
  wrapper.innerHTML = footerTemplate(height);//"<h1>break</h1>";
  //console.log(getStringFromURL("http://localhost:9000/footer/1/2", function (res) { alert(res) }));
  all[i].appendChild(wrapper.firstChild);
}

setTimeout("main()", 2000);
