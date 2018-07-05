class BooksController {

    /**
     * Shows the book registration form
     */
    showBooksForm(ev, code) {
        let editAuthors;
        $('#overlay').show();

        /**
         * Getting the book to edit
         */
        if (code) {
            $.ajax({
                url: BOOKS_API + code,
                method: 'GET',
                success: function(data) {
                    $('#isbn').val(data.code); 
                    $('#title').val(data.title); 
                    $('#editor').val(data.editor); 
                    $('#year').val(data.publishYear);
                    $('#cover').val(data.cover);
                    editAuthors = data.authors;
                },
                error: function() {
                    $('#form-books-feedback').removeClass('alert-success').addClass('alert-danger')
                        .html('Atenção: Verifique as informações preenchidas.').show();
                    $('#overlay').hide();
                }
            });
        }

        responseElement.load('form-books.html', function() {

            let selectAuthorListGroup = $('.select-authors-list-group');
            $('#overlay').hide();
    
            // fill the authors select
            let htmlAuthors = '';
            authors.forEach(function(author) {
                editAuthors.forEach(function(edAuthor) {
                    let selected = (author.id == edAuthor.id) ? 'selected' : '';
                    htmlAuthors += '<option value="'+author.id+'" '+selected+'>'+author.name+' '+author.surname+'</option>'
                });
            });
            $('.select-authors-list').append(htmlAuthors);
    
            /**
             * 
             *  duplicates author selector
             */
            $('.add-author').on('click', function(){
                console.log('cloning authors');
                selectAuthorListGroup.clone(true).appendTo($('#appender'));
            });
    
            /**
             * 
             *  Books registration POST
             */
            $('#register-books-submit').on('click', function(ev) {
                let selectAuthors = $('.select-authors-list option:selected'),
                    arrAuthors = [];
                
                selectAuthors.each(function() {
                    arrAuthors.push({id: this.value});
                });
                
                ev.preventDefault();
                $('#overlay').show();
                let book = new Book($('#isbn').val(), $('#title').val(), $('#editor').val(), $('#year').val(), arrAuthors, $('#cover').val());
                
                methodToCall = code ? 'PUT' : 'POST';

                $.ajax({
                    url: BOOKS_API,
                    method: methodToCall,
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
    }

    /**
     * Iterates over the books and authors arrays to mount the html 
     */
    mountBookResults(searchKeyWord, data) {
        let html;
        if (searchKeyWord)
            html = '<h1>Exibindo resultados para "'+ searchKeyWord +'":</h1>';
        else
            html = '<h1>Livros</h1>';
        html += '<div class="row"><div class="card-deck">';

        // getting the books
        for (let i=0; i<data.length; i++) {
            // getting the authors
            let authors = data[i].authors,
                printAuthors = '',
                sep;
            if (authors && authors.length > 0) {
                for (let j=0; j<authors.length; j++) {
                    printAuthors += sep + authors[j].name + ', ' + authors[j].surname;
                    sep = '; ';
                }
            }
            if (i % 3 == 0) {
                html += '</div></div><span class="clear"></span><div class="row"><div class="card-deck">';
            }
            html += '<a href="?bookId='+ data[i].code +'" class="card flex-md-row book-edit width-360">' +
                        '<div class="card-body">' +
                            '<h5 class="card-title">' + data[i].title + '</h5>' +
                            '<p class="card-text">'+ 
                                data[i].publishYear +' - '+ printAuthors +'<br>'+ data[i].editor +
                                // '<a class="book-rent" href="?bookId='+ data[i].code +'">[Alugar]</a>' +
                                // '<a class="book-edit" href="?bookId='+ data[i].code +'">[Editar]</a>' +
                            '</p>' +
                        '</div>' +
                        '<div class="card-footer bg-transparent">' +
                        '</div>' +
                        '<img src="'+ BASE_URL + 'img/books/' + data[i].cover +'">' +
                    '</a>';
        }
        html += '</div></div>';
        return html;
    }

    /**
	 * Books edit
	 */
    loadEditEvent(bookController) {
        $('.book-edit').on('click', function(ev) {
            let href = $(this).attr('href');
            let code = href.replace('?bookId=', '');
            bookController.showBooksForm(null, code);
            ev.preventDefault();
        });
    }

}