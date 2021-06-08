<template>
  <div class="admin-panel">
    <div v-if="user.status == 'ACTIVE'">
      <nav class="navbar navbar-light navbar-expand-md navigation-clean-button">
        <div class="container"><a class="navbar-brand" href="#">RAF NEWS ADMIN PANEL</a><button data-toggle="collapse" data-target="#navcol-1" class="navbar-toggler"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
          <div class="collapse navbar-collapse" id="navcol-1">
            <ul class="navbar-nav mr-auto">
              <li class="nav-item"><a class="nav-link btn disabled" >{{ user.name + ' ' + user.surname }}</a></li>
              <li class="nav-item"><a class="nav-link active" @click="changeMode('CATEGORIES')" href="#">Kategorije</a></li>
              <li class="nav-item"><a class="nav-link active" @click="changeMode('NEWS')" href="#">Vesti</a></li>
              <li v-if="user.role == 'ADMIN'" class="nav-item"><a @click="changeMode('USERS')" class="nav-link active" href="#">Korisnici</a></li>
            </ul><span class="navbar-text actions"><a @click="logout" class="btn btn-light action-button" role="button" href="#">Log out</a></span>
          </div>
        </div>
      </nav>

      <div class="container">

        <div v-if="mode == 'CATEGORIES'">
          <div class="row">
            <div class="col-md-6">
              <form>
                <div class="form-group"><label>Category name:</label><input v-model="categoryToAdd.catName" type="text" class="form-control" /></div>
                <div class="form-group"><label>Category description:</label><textarea v-model="categoryToAdd.catDescription" class="form-control"></textarea></div>
                <button @click="addCategory" type="button" class="btn btn-primary">Add Category</button>
              </form>
            </div>
            <div class="col-md-6">
              <form>
                <div class="form-group"><label>Category name:</label>
                  <input v-bind:disabled="JSON.stringify(selectedCategory) == '{}'" v-model="selectedCategory.categoryName" type="text" class="form-control" /></div>
                <div class="form-group"><label>Category description:</label>
                  <textarea v-bind:disabled="JSON.stringify(selectedCategory) == '{}'" v-model="selectedCategory.categoryDescription" class="form-control"></textarea></div>
                <button type="button" @click="editCategory" v-bind:disabled="JSON.stringify(selectedCategory) == '{}'" class="btn btn-primary">Edit Category</button>
              </form>
            </div>
          </div>
          <br/>
          <div class="table-responsive">
            <table class="table">
              <thead>
              <tr>
                <th>Category name</th>
                <th>Category description</th>
                <th>Actions</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="category in categories" :key="category.id">
                <td><a href="#" @click="getArticleTableForCategory(category.id)">{{ category.categoryName }}</a></td>
                <td>{{ category.categoryDescription }}</td>
                <td>
                  <button @click="selectCategory(category.id)" type="button" class="btn btn-primary">Edit</button> |
                  <button @click="deleteCategory(category.id)" type="button" class="btn btn-primary">Delete</button>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>

        <div v-if="mode == 'NEWS'">
          <div class="container">
            <div class="table-responsive">
              <table class="table">
                <thead>
                <tr>
                  <th>Article title</th>
                  <th>Author</th>
                  <th>Publish date</th>
                  <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="article in articles" :key="article.id">
                  <td><a :href="'/article/'+article.id" target="_blank">{{ article.title }}</a></td>
                  <td>{{ getAuthorName(article.authorId) }}</td>
                  <td>Cell 2</td>
                  <td>
                    <button @click="editArticle(article.id)" type="button" class="btn btn-primary">Edit</button> |
                    <button type="button" @click="deleteArticle(article.id)" class="btn btn-primary">Delete</button>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div class="text-center container">
            <ul class="pagination">
              <li class="page-item"><a @click="previousPage" class="page-link" aria-label="Previous"><span aria-hidden="true">Previous</span></a></li>
              <li class="page-item"><a @click="nextPage" class="page-link" aria-label="Next"><span aria-hidden="true">Next</span></a></li>
            </ul>
          </div>
        </div>

        <div v-if="mode == 'USERS'">
          <div class="container">
            <button @click="newUser" type="button" class="btn btn-primary">Add a new user</button>
            <hr/>
            <div class="table-responsive">
              <table class="table">
                <thead>
                <tr>
                  <th>Full name</th>
                  <th>E-mail</th>
                  <th>Role</th>
                  <th>Activity</th>
                  <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="user in users" :key="user.id">
                  <td>{{ user.name }} {{ user.surname }}</td>
                  <td>{{ user.email }}</td>
                  <td>{{ user.role }}</td>
                  <td>{{ user.status }}</td>
                  <td>
                    <button @click="editUser(user.id)" type="button" class="btn btn-primary">Edit</button> |
                    <button v-bind:disabled="user.role == 'ADMIN'" @click="changeStatus(user)" type="button" class="btn btn-primary">Change status</button>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>

      </div>
    </div>
    <div v-else>
      <h1>ACCOUNT LABELED AS INACTIVE. ACCESS DENIED!</h1>
      <button type="button" @click="logout" class="btn btn-primary">Log out</button>
    </div>


  </div>
</template>

<script>
export default {
  name: "AdminPanel",
  data() {
    return {
      user: JSON.parse(atob(localStorage.getItem('jwt').split('.')[1])),
      mode: 'CATEGORIES',
      categories: [],
      articles: [],
      users: [],
      pageNum: 1,
      apiUrl: '/api/articles',
      categoryToAdd: {
        catName: "",
        catDescription: ""
      },
      selectedCategory: {}
    }
  },
  methods: {
    logout() {
      localStorage.removeItem('jwt')
      this.$router.push('/login')
    },
    changeMode(mode) {
      this.mode = mode
      this.pageNum = 1
      this.apiUrl = '/api/articles'
      this.$axios.get(this.apiUrl).then(response => this.articles = response.data.data.articles)
    },
    getAuthorName(id) {
      for (const user of this.users) {
        if (user.id == id) {
          return user.name + " " + user.surname
        }
      }
    },
    nextPage() {
      this.pageNum++
      this.$axios.get(`${this.apiUrl}?page=${this.pageNum}`)
          .then(response => {
            if (response.data.status == "SUCCESS") {
              this.articles = response.data.data.articles
            }
          })
    },
    previousPage() {
      this.pageNum--
      if (this.pageNum < 1) this.pageNum = 1
      this.$axios.get(`${this.apiUrl}?page=${this.pageNum}`)
          .then(response => {
            if (response.data.status == "SUCCESS") {
              this.articles = response.data.data.articles
            }
          })
    },
    getArticleTableForCategory(id) {
      this.apiUrl = `/api/articles/${id}`
      this.mode = 'NEWS'
      this.$axios.get(this.apiUrl)
        .then(response => {
          this.articles = response.data.data.articles
        })
    },
    deleteCategory(id) {
      this.$axios.delete(`/api/category/${id}`)
        .then(response => {
          if (response.data.status == "SUCCESS") {
            alert('Category deleted successfully')
            window.location.reload()
          } else {
            alert('Category not deleted, articles exist in given category')
          }
        })
        .catch(err => alert(err.message))
    },
    selectCategory(id) {
      for (const category of this.categories) {
        if (category.id == id) {
          this.selectedCategory = category
        }
      }
    },
    addCategory() {
      if (this.categoryToAdd.catName.trim() && this.categoryToAdd.catDescription.trim()) {
        this.$axios.post('/api/category',{
          categoryName: this.categoryToAdd.catName,
          categoryDescription: this.categoryToAdd.catDescription
        })
        .then(response => {
          if (response.data.status == "SUCCESS") {
            alert("Category added successfully")
            window.location.reload()
          }
        })
        .catch(() => {
          alert('Category with a given name already exists')
        })
      } else {
        alert("Please fill in all the fields")
      }
    },
    editCategory() {
      if (this.selectedCategory.categoryName.trim() && this.selectedCategory.categoryDescription.trim()) {
        this.$axios.put(`/api/category/${this.selectedCategory.id}`,{
          id: this.selectedCategory.id,
          categoryName: this.selectedCategory.categoryName,
          categoryDescription: this.selectedCategory.categoryDescription
        })
        .then(response => {
          if (response.data.status == "SUCCESS") {
            alert('Category updated successfully')
            window.location.reload()
          }
        })
      } else {
        alert('Please fill in all the fields')
      }
    },
    editArticle(id) {
      this.$router.push(`/cms/article/edit/${id}`)
    },
    newUser() {
      this.$router.push('/cms/user/add')
    },
    editUser(id) {
      this.$router.push(`/cms/user/edit/${id}`)
    },
    changeStatus(user) {
      this.$axios.put(`/api/user/${user.id}`, {
        name: user.name,
        surname: user.surname,
        email: user.email,
        role: user.role,
        status: (user.status == 'ACTIVE') ? 'INACTIVE' : 'ACTIVE'
      })
      .then(response => {
        if (response.data.status == "SUCCESS") {
          alert('Changed users activity')
          window.location.reload()
        }
      })
    },
    deleteArticle(id) {
      this.$axios.delete(`/api/article/${id}`)
        .then(response => {
          if (response.data.status == "SUCCESS") {
            alert('Article deleted successfully')
            window.location.reload()
          }
        })
    }
  },
  mounted() {
    this.$axios.get('/api/category')
      .then(response => {
        this.categories = response.data.data
      })

    this.$axios.get('/api/articles')
      .then(response => {
        this.articles = response.data.data.articles
      })

    this.$axios.get('/api/users')
      .then(response => {
        this.users = response.data.data
      })
  }
}
</script>

<style scoped>

</style>