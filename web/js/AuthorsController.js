class AuthorsController {

    /**
     * Loads the Authors list
     */
    loadList(authors) {
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
    }

    /**
     * Iterates over the authors to mount html
     */
    mountAuthorsResults(searchKeyWord, data) {
        let html = '';
        console.log(data);
        if (searchKeyWord)
            html = '<h1>Exibindo resultados para "'+ searchKeyWord +'":</h1>';
        else
            html = '<h1>Autores</h1>';

        html += `<table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Nome</th>
                <th scope="col">Sobrenome</th>
                <th scope="col">País</th>
                <th scope="col">&nbsp;</th>
            </tr>
            </thead>
        <tbody>`;
        for (let author of data) {
            html += `<tr>
                    <th scope="row">${author.id}</th>
                    <td>${author.name}</td>
                    <td>${author.surname}</td>
                    <td>${author.country}</td>
                    <td><a href="${author.id}" class="btn btn-secondary btn-sm author-edit">Editar</a></td>
                </tr>`;
        }
        html += `</tbody>
        </table>`;
        return html;
    }

    /**
     * Shows the authors form
     */
    showAuthorsForm(ev, id) {
        ev.preventDefault();
        $('#overlay').show();

        /**
         * Getting the author to edit
         */
        if (id) {
            $.ajax({
                url: AUTHORS_API + id,
                method: 'GET',
                success: function(data) {
                    $('#author-id').val(data.id); 
                    $('#name').val(data.name); 
                    $('#surname').val(data.surname); 
                    $('#country').val(data.country);
                },
                error: function() {
                    $('#form-books-feedback').removeClass('alert-success').addClass('alert-danger')
                        .html('Atenção: Verifique as informações preenchidas.').show();
                    $('#overlay').hide();
                }
            });
        }

        responseElement.load('form-authors.html', function() {
            $('#overlay').hide();

            $('#author-registration').submit(function(ev) {
                ev.preventDefault();
                $('#form-authors-feedback').hide();

                if ($('#name').val() == '' || $('#surname').val() == '' || $('#country').val() == '') {
                    $('#form-authors-feedback').removeClass('alert-success').addClass('alert-danger')
                        .html('Atenção: Verifique as informações preenchidas.').show();
                } else {
                    let authorId = id ? id : null;
                    let methodToCall = id ? 'PUT' : 'POST';
                    let author = new Author(authorId, $('#name').val(), $('#surname').val(), $('#country').val());

                    $.ajax({
                        url: AUTHORS_API + id,
                        method: methodToCall,
                        dataType: 'json',
                        headers: {'Content-Type': 'application/json'},
                        data: JSON.stringify(author),
                        success: function() {
                            $('#overlay').hide();
                            $('#form-authors-feedback').removeClass('alert-danger').addClass('alert-success')
                                .html('Autor salvo com sucesso!').show();
                            $('#overlay').hide();
                        },
                        error: function() {
                            $('#form-authors-feedback').removeClass('alert-success').addClass('alert-danger')
                                .html('Atenção: Verifique as informações preenchidas.').show();
                            $('#overlay').hide();
                        }
                    });
                }
            });
        });
    }

    search(searchKeyWord) {
		$('#overlay').show();
		$.ajax({
			url: AUTHORS_API,
			method: 'GET',
			success: function(data) {
				if (data.length > 0) {
					html = authorController.mountAuthorsResults(searchKeyWord, data);
					responseElement.html(html);
					authorController.loadEditEvent(authorController);
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
    }
    
    /**
	 * Authors edit
	 */
    loadEditEvent(authorsController) {
        $('.author-edit').on('click', function(ev) {
            ev.preventDefault();
            let id = $(this).attr('href');
            authorsController.showAuthorsForm(ev, id);
        });
    }

}