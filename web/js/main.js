/**
* Declare some global variables
*/
const BASE_URL = 'http://localhost:8080/lab2-Biblioteca/',
	  BOOKS_API = BASE_URL + 'api/books/',
	  AUTHORS_API = BASE_URL + 'api/authors/',
	  responseElement = $('article#response');
let   searchKeyWord = null,
	  authors = [];


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
	 * Loads the Authors list
	 */
	$.ajax({
		url: AUTHORS_API,
		method: 'GET',
		success: function(data) {
			// console.log(JSON.stringify(data));
			data.map(el => authors.push(new Author(el.id, el.name, el.surname, el.country)));
		},
		error: function() {
			console.log('WARNING: Error geting the AUTHORS list.');
		}
	});

	/**
	 * Books registration form
	 */
	$('#register-books').click(function() {
		$('#overlay').show();
		responseElement.load('form-books.html', function() {
			let selectAuthorListGroup = $('.select-authors-list-group');
			$('#overlay').hide();

			// fill the authors select
			let htmlAuthors = '';
			authors.forEach(function(author) {
				htmlAuthors += '<option value="'+author.id+'">'+author.name+' '+author.surname+'</option>'
			});
			$('.select-authors-list').append(htmlAuthors);

			// duplicates author selector
			$('.add-author').on('click', function(){
				console.log('cloning authors');
				selectAuthorListGroup.clone(true).appendTo($('#appender'));
			});

			// Books registration POST
			$('#register-books-submit').on('click', function(ev) {
				let book = new Book(
					$('#isbn').val(),
					$('#title').val(),
					$('#editor').val(),
					$('#year').val(),
					$('#author').val() == 'Selecione...' ? [] : [$('#author').val()],
					$('#cover').val()
				);
				$('#overlay').show();
				ev.preventDefault();
				$.ajax({
					url: BOOKS_API,
					method: 'POST',
					dataType: 'json',
					headers: {'Content-Type': 'application/json'},
					data: JSON.stringify(book),
					success: function() {
						$('#overlay').hide();
						$('#form-books-feedback').removeClass('alert-danger').addClass('alert-success')
							.html('Livro salvo com sucesso!').show();
						$('#overlay').hide();
					},
					error: function() {
						$('#form-books-feedback').removeClass('alert-success').addClass('alert-danger')
							.html('Atenção: Verifique as informações preenchidas.').show();
						$('#overlay').hide();
					}
				});
			});
		});
	});

	
});