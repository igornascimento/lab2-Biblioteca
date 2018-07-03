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

}