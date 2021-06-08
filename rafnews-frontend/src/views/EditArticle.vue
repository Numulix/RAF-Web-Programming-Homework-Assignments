<template>
  <div class="edit-article">
    <div v-if="user.status == 'ACTIVE'">
      <div class="container">
        <h1>Edit Article</h1>
        <form>
          <div class="form-group"><label>Article title:</label><input v-model="article.title" type="text" class="form-control" /></div>
          <div class="form-group"><label>Category:</label><select v-model="article.categoryId" class="form-control">
            <option v-for="category in categories" :key="category.id" :value="category.id">{{ category.categoryName }}</option>
          </select></div>
          <div class="form-group"><label>Article title:</label><textarea v-model="article.content" class="form-control"></textarea></div>
          <div class="form-group"><label>Tags:</label><input v-model="tags" type="text" class="form-control" /><small>Separate tag names with a &#39;,&#39;, i.e &quot;tag 1,tag 2,tag 3&quot;</small></div>
          <button @click="editArticle" type="button" class="btn btn-primary">Edit Article</button>
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
  name: "EditArticle",
  data() {
    return {
      user: JSON.parse(atob(localStorage.getItem('jwt').split('.')[1])),
      categories: [],
      article: {},
      tags: "",
      tagsArray: []
    }
  },
  mounted() {
    this.$axios.get('/api/category')
      .then(response => {
        if (response.data.status == "SUCCESS") {
          this.categories = response.data.data
        }
      })

    this.$axios.get(`/api/article/${this.$route.params.id}`)
      .then(response => {
        if (response.data.status == "SUCCESS") {
          this.article = response.data.data
        }
      })


    this.$axios.get(`/api/tags`)
      .then(response => {
        if (response.data.status == "SUCCESS") {
          this.tagsArray = response.data.data
          return this.$axios.get(`/api/tags/${this.$route.params.id}`)
        }
      })
        .then(response => {
          if (response.data.status == "SUCCESS") {
            for (const tag of response.data.data) {
              for (const tagName of this.tagsArray) {
                if (tagName.id == tag) {
                  this.tags += tagName.tagName + ','
                }
              }
            }
            this.tags = this.tags.slice(0, -1)
          }
        })
  },
  methods: {
    editArticle() {
      if (this.article.title.trim() && this.article.categoryId
          && this.tags.trim() && this.article.content.trim()) {
        this.$axios.put(`/api/article/${this.$route.params.id}`,{
          categoryId: this.article.categoryId,
          title: this.article.title,
          content: this.article.content,
          tags: this.tags
        })
            .then(response => {
              if (response.data.status == "SUCCESS") {
                alert('Article edited successfully')
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