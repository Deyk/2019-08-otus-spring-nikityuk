const authorsListPath = '/components/authorList.vue';
const authorsEditPath = '/components/authorEdit.vue';
const booksListPath = '/components/bookList.vue';
const booksEditPath = '/components/bookEdit.vue';

let router = new VueRouter({
    routes: [
        {
            path: '/authors',
            name: 'authors',
            component: httpVueLoader(authorsListPath)
        },
        {
            path: '/authors/:id',
            name: 'authorEdit',
            component: httpVueLoader(authorsEditPath),
            props: true
        },
        {
            path: '/books',
            name: 'books',
            component: httpVueLoader(booksListPath)
        },
        {
            path: '/books/:id',
            name: 'bookEdit',
            component: httpVueLoader(booksEditPath),
            props: true
        }
    ]
});

new Vue({
    components: {
        'author-list': httpVueLoader(authorsListPath),
        'author-edit': httpVueLoader(authorsEditPath),
        'book-list': httpVueLoader(booksListPath),
        'book-edit': httpVueLoader(booksEditPath)
    },
    router
}).$mount('#library');