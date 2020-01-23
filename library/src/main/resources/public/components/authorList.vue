<template>
<!--    <nav class="navbar navbar-expand-lg navbar-light bg-light">-->
<!--        <a class="navbar-brand" href="#">Library</a>-->
<!--        <div class="collapse navbar-collapse" id="navbarNav">-->
<!--            <ul class="navbar-nav">-->
<!--                <li class="nav-item">-->
<!--                    <router-link to="/books" class="nav-link" href="bookList.html">Books</router-link>-->
<!--                </li>-->
<!--                <li class="nav-item active">-->
<!--                    <router-link to="/authors" class="nav-link" href="authorList.html">Authors</router-link>-->
<!--                </li>-->
<!--            </ul>-->
<!--        </div>-->
<!--    </nav>-->

    <div id="authorList">
        <div class="h5">Add author:</div>
        <form id="add-author"
              @submit.prevent="createAuthor">
            <div class="form-group form-row align-items-center">
                <div class="col">
                    <input v-model.trim="authorName" class="form-control" name="name" type="text" placeholder="Name"/>
                </div>
                <div class="col">
                    <button class="btn btn-primary" type="submit">Create</button>
                </div>
            </div>
        </form>

        <table class="table table-hover">
            <caption>List of all authors</caption>
            <thead class="thead-light">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Name</th>
                <th scope="colgroup" colspan="2">Actions</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="author in authors">
                <td>{{author.id}}</td>
                <td>{{author.name}}</td>
                <td>
                    <button class="btn btn-primary btn-sm" type="submit">Edit</button>
                </td>
                <td>
                    <button class="btn btn-danger btn-sm" type="submit"
                            @click="deleteAuthor(author.id)">Delete
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    module.exports = {
        name: 'author-list',
        props: [],
        data: function () {
            return {
                authors: {},
                id: '',
                authorName: ''
            }
        },
        created: function () {
            this.getAuthors();
        },
        methods: {
            getAuthors: function () {
                let that = this;
                fetch('/authors')
                    .then(response => response.json())
                    .then(authors => {
                        that.authors = authors
                    });
            },
            createAuthor: function () {
                let that = this;
                const options = {
                    method: 'POST',
                };
                fetch('/authors/add?authorName=' + this.authorName, options)
                    .then(response => response.json())
                    .then(author => {
                        if (author.error) {
                            console.log(author.message)
                        } else {
                            that.authors.push(author)
                        }
                    })
            },
            deleteAuthor: function (id) {
                let that = this;
                const options = {
                    method: 'DELETE',
                };
                fetch('/authors/delete?id=' + id, options)
                    .then(response => response.json())
                    .then(response => {
                        if (response.error || response.false) {
                            console.log(response.message)
                        } else {
                            that.getAuthors();
                        }
                    })
            },
            editAuthor: function () {
                let that = this;
                let payload = {
                    id: this.id,
                    authorName: this.authorName
                };
                const options = {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json;charset=utf-8'
                    },
                    body: JSON.stringify(payload)
                };
                fetch('/authors/edit', options)
                    .then(response => response.json())
                    .then(response => {
                        if (response.error) {
                            console.log(response.message)
                        } else {
                            that.getAuthors();
                        }
                    })
            }
        }
    }
</script>