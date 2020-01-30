<template>
    <div id="authorList">
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

        <div class="h5">Add author:</div>

        <form id="add-author"
              @submit.prevent="createAuthor">
            <div class="form-group form-row align-items-center">
                <div class="col">
                    <input v-model.trim="authorName" class="form-control" type="text" placeholder="Name"/>
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
                    <router-link :to="{name: 'authorEdit', params:{id: author.id, authorName: author.name}}"
                                 class="btn btn-primary btn-sm">Edit
                    </router-link>
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
        data: function () {
            return {
                authors: {},
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
                    .then(response => response.text())
                    .then(response => {
                        if (response !== 'true') {
                            alert(response)
                        } else {
                            that.getAuthors();
                        }
                    })
            }
        }
    }
</script>