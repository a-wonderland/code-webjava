/**
 *
 */

$(function(){
    var dataContainer = $('#data-container');
    var paginationContainer = $('#pagination-container');
	var $searchForm = $("#searchForm");
    var data = {};
		$searchForm.submit(function(event){
            // remove old data
            data = {};
			event.preventDefault(); // Prevent the form from submitting via the browser, if not it will request to xxx.jsp
			$.ajax({
	             dataType: 'html',
				 type: $searchForm.attr('method'),
			    // url: './searchActor2.do', // [./] use
			     url: $searchForm.attr('action'),
			     data: $searchForm.serialize(), // serializes the form's elements.
		         success: function (data) {
		              console.log('Submission was successful.');
                      data = JSON.parse(data);
		              if(data.status === 'OK'){
		            	   		   paginationContainer.pagination({
	                               dataSource: data,
	                               locator: getLocator(),
	                               totalNumber: data.total,
	                               pageSize: 20,
	                               className: 'paginationjs-theme-yellow',
	                               showNavigator: true,
	                               showPrevious: true,
	                               showNext: true,
	                               callback: function(response, pagination) {
	                            	   $('#msg').html('');
	                                   // template
	                                   var html = template(response);
	                                   dataContainer.html(html);
	                               }
	                           });
		              }else {
                          // remove old data
                          dataContainer.html('');
                          paginationContainer.html('');
		            	  $('#msg').html("No Record Found!");
		              }
		          },
		         error: function (data, status, thrown) {
		                console.log(status+' An error occurred.');
                        // remove old data
                        dataContainer.html('');
                        paginationContainer.html('');
		         },
			});
	   });
});

