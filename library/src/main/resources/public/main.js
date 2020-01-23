const authorsListPath = '/components/authorList.vue';

let router = new VueRouter({
    routes: [
        {
            path: '/authors',
            component: httpVueLoader(authorsListPath)
        }
    ]
});

new Vue({
    components: {
        'author-list': httpVueLoader(authorsListPath)
    },
    router
}).$mount('#library');