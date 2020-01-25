<template>
    <div id="bookList">
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

        <div class="h5">Add book:</div>

        <form id="add-book"
              method="post"
              @submit.prevent="createBook">
            <div class="form-group form-row align-items-center">
                <div class="col">
                    <input v-model="title" class="form-control" type="text" placeholder="Title"/>
                </div>
                <div class="col">
                    <input v-model="authorName" class="form-control" type="text" placeholder="Author name"/>
                </div>
                <div class="col">
                    <button class="btn btn-primary" type="submit">Create</button>
                </div>
            </div>
        </form>

        <table class="table table-hover">
            <caption>List of all books</caption>
            <thead class="thead-light">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Title</th>
                <th scope="col">Authors</th>
                <th scope="colgroup" colspan="2">Actions</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="book in books">
                <th>{{book.id}}</th>
                <td>{{book.title}}</td>
                <td>
                    <div v-for="author in book.authors">
                        <div>{{author.name}}</div>
                    </div>
                </td>
                <td>
                    <router-link :to="{name: 'bookEdit', params:{id: book.id, title: book.title}}"
                                 class="btn btn-primary btn-sm">Edit
                    </router-link>
                </td>
                <td>
                    <button class="btn btn-danger btn-sm" type="submit"
                            @click="deleteBook(book.id)">Delete
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    module.exports = {
        name: 'book-list',
        data: function () {
            return {
                books: {},
                title: '',
                authorName: ''
            }
        },
        created: function () {
            this.getBooks();
        },
        methods: {
            getBooks: function () {
                let that = this;
                fetch('/books')
                    .then(response => response.json())
                    .then(books => {
                        that.books = books
                    });
            },
            createBook: function () {
                let that = this;
                const options = {
                    method: 'POST',
                };
                fetch('/books/add?title=' + this.title + '&authorName=' + this.authorName, options)
                    .then(response => response.json())
                    .then(book => {
                        if (book.error) {
                            console.log(book.message)
                        } else {
                            that.books.push(book)
                        }
                    })
            },
            deleteBook: function (id) {
                let that = this;
                const options = {
                    method: 'DELETE',
                };
                fetch('/books/delete?id=' + id, options)
                    .then(response => response.text())
                    .then(response => {
                        if (response !== 'true') {
                            alert(response)
                        } else {
                            that.getBooks();
                        }
                    })
            },
            getAuthorNames: function (book) {
                let map = book.authors.map(author => {
                    return author.name;
                });
                console.log(map.toString());
                return map.toString();
            }
        }
    }
</script>