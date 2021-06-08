<template>
  <div class="single-article">
    <Navbar/>
    <section class="article-clean">
      <div class="container">
        <div class="row">
          <div class="col-lg-10 col-xl-8 offset-lg-1 offset-xl-2">
            <div class="intro">
              <h1 class="text-center">{{ article.title }}</h1>
              <p class="text-center"><span class="by">by </span><a href="#">{{ authorName }}</a> | <span class="date">{{ article.publishDate }}</span></p>
              <div class="text-center">
                <a v-for="tagId in tagsForPost" :key="tagId" :href="'/t/'+tagId">#{{ getTagName(tagId) }} </a>
              </div>
            </div>
            <div class="text">
              <p>{{ article.content }}</p>
            </div>
          </div>
        </div>
      </div>
    </section>
    <div class="container">
      <div class="row">
        <div class="col col-md-8">
          <h1>Comments</h1>
          <div class="card" v-for="comment in commentsForPost" :key="comment.id">
            <div class="card-body">
              <h4 class="card-title">{{ comment.author }}</h4>
              <h6 class="text-muted card-subtitle mb-2">{{ comment.publishDate }}</h6>
              <p class="card-text">{{ comment.content }}</p>
            </div>
          </div>
        </div>
        <div class="col col-md-4">
          <form>
            <div class="form-group"><label>Comment author:</label><input v-model="newCommentAuthor" required type="text" class="form-control" /></div>
            <div class="form-group"><label>Comment text:</label><textarea v-model="newCommentText" required class="form-control"></textarea></div>
            <button class="btn btn-primary" type="button" @click="postComment">Submit comment</button>
          </form>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import Navbar from "../components/Navbar";

export default {
  name: "Article",
  components: {
    Navbar
  },
  data() {
    return {
      article: {},
      authorName: "",
      newCommentAuthor: "",
      newCommentText: "",
      commentsForPost: [],
      tags: [],
      tagsForPost: []
    }
  },
  created() {
    this.$axios.get(`/api/article/${this.$route.params.id}`)
      .then(response => {
        if (response.data.status == "SUCCESS") {
          this.article = response.data.data
          return this.$axios.get(`/api/user/${this.article.authorId}`)
        }
      })
      .then(response => {
        if (response.data.status == "SUCCESS") {
          this.authorName = response.data.data.name + " " + response.data.data.surname
          return this.$axios.get(`/api/comments/${this.$route.params.id}`)
        }
      })
      .then(response => {
        if (response.data.status == "SUCCESS") {
          this.commentsForPost = response.data.data
        }
      })

    this.$axios.get(`/api/tags`)
      .then(response => {
        if (response.data.status == "SUCCESS") {
          this.tags = response.data.data
        }
      })

    this.$axios.get(`/api/tags/${this.$route.params.id}`)
      .then(response => {
        if (response.data.status == "SUCCESS") {
          this.tagsForPost = response.data.data
        }
      })
  },
  methods: {
    postComment() {
      if (this.newCommentAuthor.trim() && this.newCommentText.trim()) {
        this.$axios.post(`/api/comment`, {
          postId: this.$route.params.id,
          author: this.newCommentAuthor,
          content: this.newCommentText
        })
        .then(response => {
          if (response.data.status == "SUCCESS") {
            console.log(response.data)
            window.location.reload();
          }
        })
      }
    },

    getTagName(id) {
      for (const tag of this.tags) {
        if (tag.id === id) {
          return tag.tagName
        }
      }
    }
  }
}
</script>

<style scoped>

</style>