<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">Library</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <router-link :to="{name: 'books'}" class="nav-link">Books</router-link>
                </li>
                <li class="nav-item active">
                    <router-link :to="{name: 'authors'}" class="nav-link">Authors</router-link>
                </li>
            </ul>
        </div>
    </nav>


</template>

<script>
    module.exports = {
        name: 'book-list',
        data: function () {
            return {}
        },
        // created: function () {
        //     this.getBooks();
        // },
        // methods: {
        //     getBooks: function () {
        //         let that = this;
        //         fetch('/authors')
        //             .then(response => response.json())
        //             .then(authors => {
        //                 that.authors = authors
        //             });
        //     },
        //     createAuthor: function () {
        //         let that = this;
        //         const options = {
        //             method: 'POST',
        //         };
        //         fetch('/authors/add?authorName=' + this.authorName, options)
        //             .then(response => response.json())
        //             .then(author => {
        //                 if (author.error) {
        //                     console.log(author.message)
        //                 } else {
        //                     that.authors.push(author)
        //                 }
        //             })
        //     },
        //     deleteAuthor: function (id) {
        //         let that = this;
        //         const options = {
        //             method: 'DELETE',
        //         };
        //         fetch('/authors/delete?id=' + id, options)
        //             .then(response => response.text())
        //             .then(response => {
        //                 if (response !== 'true') {
        //                     alert(response)
        //                 } else {
        //                     that.getAuthors();
        //                 }
        //             })
        //     }
        // }
    }
</script>