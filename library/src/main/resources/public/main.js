new Vue({
    components: {
        'author-list': httpVueLoader('/components/authorList.vue')
    },
}).$mount('#library');