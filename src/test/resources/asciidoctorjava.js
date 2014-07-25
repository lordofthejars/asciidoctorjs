var render = function(content, optionsHash2) {
    return Opal.Asciidoctor.$render(content, optionsHash2);
};

var hello = function() {
  return "Hello Nashorn!";
};