import authorList from './components/authorList.js';

new Vue({
    components: {
        'author-list': authorList
    },
}).$mount('#library');