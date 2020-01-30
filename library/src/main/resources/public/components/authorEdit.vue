<template>
    <div id="authorEdit">
        <form id="edit-form"
              @submit.prevent="editAuthor">
            <div class="display-4 text-center text-uppercase">Author Info:</div>

            <div class="form-group">
                <label for="id-input">ID:</label>
                <input id="id-input"
                       v-model="id"
                       class="form-control"
                       type="text"
                       readonly/>
            </div>

            <div class="form-group">
                <label for="name-input">Name:</label>
                <input id="name-input"
                       v-model.trim="authorName"
                       class="form-control"
                       type="text"/>
            </div>

            <button class="btn btn-primary" type="submit">Save</button>
        </form>
    </div>
</template>

<script>
    module.exports = {
        name: 'author-edit',
        props: ['id', 'authorName'],
        data: function () {
            return {
                id: id,
                authorName: authorName
            }
        },
        methods: {
            editAuthor: function () {
                let that = this;
                let payload = {
                    id: this.id,
                    name: this.authorName
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
                            that.$router.push({name: 'authors'});
                        }
                    })
            }
        }
    }
</script>