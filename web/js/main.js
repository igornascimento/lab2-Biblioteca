/**
* Declare some global variables
*/
const BASE_URL = 'http://localhost:8080/lab2-Biblioteca/',
	  BOOKS_API = BASE_URL + 'api/books/',
	  responseElement = $('article#response');
let   searchKeyWord = null;


/**
 * Iterates over the books and authors arrays to mount the html 
 */
function mountBookResults(data) {
	let html;
	if (searchKeyWord)
		html = '<h1>Exibindo resultados para "'+ searchKeyWord +'":</h1>';
	else
		html = '<h1>Livros</h1>';
	html += '<div class="row"><div class="card-deck">';
	// getting the books
	for (let i=0; i<data.length; i++) {
		// getting the authors
		let authors = data[i].author,
			printAuthors,
			sep;
		if (authors && authors.length > 0) {
			for (let j=0; j<authors.length; j++) {
				printAuthors += sep + authors[j].lastName + ', ' + authors[j].firstName;
				sep = '; ';
			}
		}
		if (i % 3 == 0) {
			html += '</div></div><span class="clear"></span><div class="row"><div class="card-deck">';
		}
		html += '<div class="card flex-md-row">' +
					'<div class="card-body">' +
						'<h5 class="card-title">' + data[i].title + '</h5>' +
						'<h6 class="card-subtitle">'+ data[i].year +' - '+ printAuthors +'</h6>' +
						'<p class="card-text">'+ data[i].shortDescription +'</p>' +
					'</div>' +
					'<img src="'+ BASE_URL + 'img/books/' + data[i].cover +'">' +
				'</div>';
	}
	html += '</div></div>';
	return html;
}


$(document).ready(function() {
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
				html = mountBookResults(data);
				$('#overlay').hide();
				responseElement.html(html);
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
	$('#list-books').click(function() {
		$('#overlay').show();

		$.ajax({
			url: BOOKS_API,
			method: 'GET',
			success: function(data) {
				html = mountBookResults(data);
				$('#overlay').hide();
				responseElement.html(html);
			},
			error: function() {
				html = '<h1>Oops!</h1><p>Não foram encontrados resultados.</p>';
			}
		});
		ev.preventDefault();
	});

	/**
	 * Books registration form
	 */
	$('#register-books').click(function() {
		responseElement.load('form-books.html', function() {
			// Books registration POST
			$('#register-books-submit').on('click', function(ev) {
				ev.preventDefault();
				$.ajax({
					url: BOOKS_API,
					method: 'POST',
					data: {
						id: $('#isbn').val(),
						title: $('#title').val(),
						editor: $('#editor').val(),
						publish_year: $('#year').val(),
						cover: $('#cover').val()
					},
					success: function() {
						$('#overlay').hide();
						$('#form-books-feedback').removeClass('alert-danger').addClass('alert-success')
							.html('Livro salvo com sucesso!');
					},
					error: function() {
						$('#form-books-feedback').removeClass('alert-success').addClass('alert-danger')
							.html('Atenção: Verifique as informações preenchidas.');
					}
				});
			});
		});
	});

	
});