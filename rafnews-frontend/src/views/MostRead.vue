<template>
  <div class="most-read">
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
  </div>
</template>

<script>
import Navbar from "../components/Navbar";

export default {
  name: "MostRead",
  components: {
    Navbar
  },
  data() {
    return {
      articles: [],
      categories: []
    }
  },
  mounted() {
    this.$axios.get('/api/popular')
        .then(response => {
          if (response.data.status == "SUCCESS") {
            this.articles = response.data.data
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