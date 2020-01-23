<template>
    <div id="authorList">
        <div class="h5">Add author:</div>
        <form id="add-author">
            <div class="form-group form-row align-items-center">
                <div class="col">
                    <input id="holder-input" class="form-control" name="name" type="text" placeholder="Name"/>
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
                    <button class="btn btn-sm" type="submit">Edit</button>
                </td>
                <td>
                    <button class="btn btn-danger btn-sm" type="submit">Delete</button>
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
                authors: null
            }
        },
        created: function () {
            var that = this;
            fetch('/authors')
                .then(response => response.json())
                .then(authors => {
                    that.authors = authors
                });
        },
        methods: {}
    }
</script>