var app = new Vue({
    el: "#main",
    data: {
        posts: null,
        commentsForPost: null,
        currentPost: {},
        mode: "BROWSE",
        newPostAuthor: "",
        newPostTitle: "",
        newPostContent: "",
        newCommentAuthor: "",
        newCommentContent: ""
    },
    mounted() {
        axios
            .get('/api/posts')
            .then(res => {
                this.posts = res.data
            })
    },
    methods: {
        newPostMode: function () {
            this.mode = "NEW_POST"
        },
        browseMode: function () {
            this.mode = "BROWSE"
            this.currentPost = null
            this.commentsForPost = {}
        },
        savePost: function () {
            if (this.newPostAuthor.trim().length !== 0
                && this.newPostTitle.trim().length !== 0
                && this.newPostContent.trim().length !== 0) {
                axios
                    .post('/api/posts', {
                        author: this.newPostAuthor,
                        title: this.newPostTitle,
                        content: this.newPostContent
                    })
                    .then(() => {
                        this.newPostAuthor = ''
                        this.newPostTitle = ''
                        this.newPostContent = ''
                        return axios.get('/api/posts')
                    })
                    .then(res => {
                        this.posts = res.data
                        this.mode = 'BROWSE'
                    })
            }
        },
        setCurrentPost: function (post) {
            this.currentPost = post
            axios
                .get('/api/comments/' + post.id)
                .then(res => {
                    this.commentsForPost = res.data
                })
            this.mode = "SINGLE_POST"
        },
        comment: function () {
            if (this.newCommentAuthor.trim().length !== 0
                && this.newCommentContent.trim().length !== 0) {
                axios
                    .post('/api/comments/' + this.currentPost.id, {
                        author: this.newCommentAuthor,
                        content: this.newCommentContent
                    })
                    .then(() => {
                        this.commentsForPost.push({
                            author: this.newCommentAuthor,
                            content: this.newCommentContent
                        })
                        this.newCommentAuthor = ''
                        this.newCommentContent = ''
                    })

            }
        }
    },
    filters: {
        dateFormat: function (value) {
            return moment(value).format('DD.MM.YYYY')
        },
        shortenText: function (str) {
            if (str.length > 301) {
                return str.substring(0, 301) + '...'
            }
            return str
        }
    }
})