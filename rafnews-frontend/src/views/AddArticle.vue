<template>
  <div class="add-article">
    <div v-if="user.status == 'ACTIVE'">
      <div class="container">
        <h1>New Article</h1>
        <form>
          <div class="form-group"><label>Article title:</label><input v-model="article.title" type="text" class="form-control" /></div>
          <div class="form-group"><label>Category:</label><select v-model="article.category" class="form-control">
            <option v-for="category in categories" :key="category.id" :value="category.id">{{ category.categoryName }}</option>
          </select></div>
          <div class="form-group"><label>Article title:</label><textarea v-model="article.content" class="form-control"></textarea></div>
          <div class="form-group"><label>Tags:</label><input v-model="article.tags" type="text" class="form-control" /><small>Separate tag names with a &#39;,&#39;, i.e &quot;tag 1,tag 2,tag 3&quot;</small></div>
          <button @click="addArticle" type="button" class="btn btn-primary">Add Article</button>
        </form>
      </div>
    </div>
    <div v-else>
      <h1>ACCOUNT LABELED AS INACTIVE. ACCESS DENIED!</h1>
    </div>

  </div>
</template>

<script>
export default {
  name: "AddArticle",
  data() {
    return {
      user: JSON.parse(atob(localStorage.getItem('jwt').split('.')[1])),
      categories: [],
      article: {
        title: "",
        category: "",
        tags: "",
        content: ""
      }
    }
  },
  mounted() {
    this.$axios.get('/api/category')
      .then(response => {
        if (response.data.status == "SUCCESS") {
          this.categories = response.data.data
        }
      })

  },
  methods: {
    addArticle() {
      if (this.article.title.trim() && this.article.category
      && this.article.tags.trim() && this.article.content.trim()) {
        this.$axios.post('/api/article',{
          categoryId: this.article.category,
          title: this.article.title,
          content: this.article.content,
          authorId: this.user.id,
          tags: this.article.tags
        })
        .then(response => {
          if (response.data.status == "SUCCESS") {
            alert('Article added successfully')
            this.$router.push('/cms')
          }
        })
      } else {
        alert('Please fill in all fields.')
      }
    }
  }
}
</script>

<style scoped>

</style>