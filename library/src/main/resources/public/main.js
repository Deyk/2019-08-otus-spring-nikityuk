const authorsListPath = '/components/authorList.vue';
const authorsEditPath = '/components/authorEdit.vue';

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
        }
    ]
});

new Vue({
    components: {
        'author-list': httpVueLoader(authorsListPath),
        'author-edit': httpVueLoader(authorsEditPath)
    },
    router
}).$mount('#library');