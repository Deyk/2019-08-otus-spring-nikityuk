<template>
    <div id="bookEdit">
        <form id="edit-form"
              @submit.prevent="editBook">
            <div class="display-4 text-center text-uppercase">Book Info:</div>

            <div class="form-group">
                <label for="id-input">ID:</label>
                <input id="id-input"
                       v-model="id"
                       class="form-control"
                       type="text"
                       readonly/>
            </div>

            <div class="form-group">
                <label for="title-input">Title:</label>
                <input id="title-input"
                       v-model.trim="title"
                       class="form-control"
                       type="text"/>
            </div>

            <select class="form-group"
                    v-model="selectedAuthor">
                <option disabled value="">Choose one author</option>
                <option v-for="name in authorNames"
                        :value="name">{{name}}
                </option>
            </select>

            <div class="form-group">
                <label for="selected-author-input">Author name:</label>
                <input id="selected-author-input"
                       v-model.trim="selectedAuthor"
                       class="form-control"
                       type="text"/>
            </div>

            <button class="btn btn-primary" type="submit">Save</button>
        </form>
    </div>
</template>

<script>
    module.exports = {
        name: 'book-edit',
        props: ['id', 'title', 'authorNames'],
        data: function () {
            return {
                id: id,
                title: title,
                authorNames: authorNames,
                selectedAuthor: ''
            }
        },
        created: function () {
            this.authorNames = this.authorNames.split(',');
            this.selectedAuthor = this.authorNames[0];
        },
        methods: {
            editBook: function () {
                let that = this;
                let payload = {
                    id: this.id,
                    title: this.title,
                    selectedAuthor: this.selectedAuthor
                };
                const options = {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json;charset=utf-8'
                    },
                    body: JSON.stringify(payload)
                };
                fetch('/books/edit', options)
                    .then(response => response.json())
                    .then(response => {
                        if (response.error) {
                            console.log(response.message)
                        } else {
                            that.$router.push({name: 'books'});
                        }
                    })
            }
        }
    }
</script>