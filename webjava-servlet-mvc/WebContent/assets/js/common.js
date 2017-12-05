/**
 *
 */

var $nav = $('.container a');
var last_clicked;

$(function() {
    var $forms = $('form');
    $forms.focusin(function() {
        $forms.removeClass('active-form');
        $(this).addClass('active-form');
    });

    // If values are not blank, restore them to the fields
    var active = "#"+sessionStorage.getItem('action');
    if (active !== null){
    	$(active).removeClass('red').addClass('active');
    }

    $('.container a').on("click", function(e){
        //e.preventDefault();
    	//console.log($(e.target).parent("li").attr('id'));
        last_clicked = $(e.target).parent("li").attr('id');
    });
});




window.onbeforeunload = function(e) {
    sessionStorage.setItem('action', last_clicked);
}