<template>
  <div class="tagged-articles">
    <Navbar/>
    <div class="container">
      <div class="row">
        <div v-for="article in articles" :key="article.id" class="col col-md-3">
          <div class="card">
            <div class="card-body">
              <h4 class="card-title">{{ article.title }}</h4>
              <h6 class="text-muted card-subtitle mb-2">{{ getCategoryName(article.categoryId) }}</h6>
              <h6 class="text-muted card-subtitle mb-2">{{ article.publishDate }}</h6>
              <p class="card-text">{{ article.content | shortText }}</p>
              <a class="card-link" :href="'/article/'+article.id">Read More</a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <br/>
    <div class="text-center container">
      <ul class="pagination">
        <li class="page-item"><a @click="previousPage" class="page-link" aria-label="Previous"><span aria-hidden="true">Previous</span></a></li>
        <li class="page-item"><a @click="nextPage" class="page-link" aria-label="Next"><span aria-hidden="true">Next</span></a></li>
      </ul>
    </div>
  </div>
</template>

<script>
import Navbar from "../components/Navbar";

export default {
  name: "TaggedArticles",
  components: {Navbar},
  data() {
    return {
      pageNum: 1,
      articles: [],
      categories: []
    }
  },
  mounted() {
    this.$axios.get(`/api/articles/tag/${this.$route.params.id}`)
        .then(response => {
          if (response.data.status == "SUCCESS") {
            this.articles = response.data.data.articles
          }
        })

    this.$axios.get('/api/category')
        .then(response => {
          if (response.data.status == "SUCCESS") {
            this.categories = response.data.data
          }
        })
  },
  methods: {
    getCategoryName(id) {
      for (const category of this.categories) {
        if (category.id == id) {
          return category.categoryName
        }
      }
    },
    nextPage() {
      this.pageNum++
      this.$axios.get(`/api/articles/tag/${this.$route.params.id}?page=${this.pageNum}`)
          .then(response => {
            if (response.data.status == "SUCCESS") {
              this.articles = response.data.data.articles
            }
          })
    },

    previousPage() {
      this.pageNum--
      if (this.pageNum < 0) this.pageNum = 1
      this.$axios.get(`/api/articles/tag/${this.$route.params.id}?page=${this.pageNum}`)
          .then(response => {
            if (response.data.status == "SUCCESS") {
              this.articles = response.data.data.articles
            }
          })
    }
  },
  filters: {
    shortText(value) {
      if (value.length < 30) {
        return value;
      }

      return value.slice(0, 30) + '...'
    }
  }
}
</script>

<style scoped>

</style>