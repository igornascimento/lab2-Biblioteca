/**
* Declare some global variables
*/
const BASE_URL = 'http://localhost:8080/lab2-Biblioteca/',
	  BOOKS_API = BASE_URL + 'api/books/',
	  AUTHORS_API = BASE_URL + 'api/authors/',
	  responseElement = $('article#response');
let   searchKeyWord = null,
	  authors = [];


$(document).ready(function() {
	/**
	 * Register menu navigation click
	 */
	let bookController = new BooksController();
	$('#register-books').click( bookController.showBooksForm );
	let authorsController = new AuthorsController();
	$('#register-authors').click( authorsController.showAuthorsForm );
	
	/**
	 * Books search
	 */
	$('#quick-search').submit(function(ev) {
		searchKeyWord = $('#quick-search-input').val();
		$('#overlay').show();

		$.ajax({
			url: BOOKS_API + 'search/',
			method: 'GET',
			data: {title: searchKeyWord},
			success: function(data) {
				if (data.length > 0) {
					html = bookController.mountBookResults(searchKeyWord, data);
					responseElement.html(html);
					bookController.loadEditEvent(bookController);
				} else {
					html = '<h1>Oops!</h1><p>Não foram encontrados resultados.</p>';	
				}
				$('#overlay').hide();
			},
			error: function() {
				html = '<h1>Oops!</h1><p>Não foram encontrados resultados para a sua pesquisa.</p>';
			}
		})
		.done(function() {
			$('#overlay').hide();
			responseElement.html(html);
		});
		ev.preventDefault();
	});

	/**
	 * Books list
	 */
	$('#list-books').click(function(ev) {
		$('#overlay').show();
		$.ajax({
			url: BOOKS_API,
			method: 'GET',
			success: function(data) {
				if (data.length > 0) {
					html = bookController.mountBookResults(searchKeyWord, data);
					responseElement.html(html);
					bookController.loadEditEvent(bookController);
				} else {
					html = '<h1>Oops!</h1><p>Não foram encontrados resultados.</p>';
				}
				$('#overlay').hide();
			},
			error: function() {
				html = '<h1>Oops!</h1><p>Não foram encontrados resultados.</p>';
				$('#overlay').hide();
			}
		});
		ev.preventDefault();
	});

	/**
	 * Authors list
	 */
	$('#list-authors').click(function(){
		let html = '';
        $('#overlay').show();
        $.ajax({
            url: AUTHORS_API,
            method: 'GET',
            success: (data) => {
                if (data.length > 0) {
					html = authorsController.mountAuthorsResults(null, data);
                    $('#overlay').hide();
                } else {
					html = '<h1>Oops!</h1><p>Não foram encontrados resultados.</p>';
				}
				responseElement.html(html);
				authorsController.loadEditEvent(authorsController);
            },
            error: () => {
                html = '<h1>Oops!</h1><p>Não foram encontrados resultados.</p>';
                $('#overlay').hide();
            }
		});
		
	});
	
});