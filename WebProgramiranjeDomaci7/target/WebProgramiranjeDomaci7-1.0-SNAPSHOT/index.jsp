<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>RAF Blog</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>

<body>

    <div id="main">

        <div v-if="mode=='BROWSE'" id="posts" class="container">
            <h1 class="text-center">Objave</h1>
            <div class="card" v-for="p in posts">
                <div class="card-body">
                    <h5 class="d-inline-block card-title">{{p.title}}</h5>
                    <p class="d-inline-block float-right margin-top">
                        <small class="text-muted">
                            {{p.publishDate | dateFormat}}
                        </small>
                    </p>
                    <p class="card-text">{{p.content | shortenText}}</p>
                    <button v-on:click="setCurrentPost(p)" class="btn btn-primary">Opsirnije</button>
                </div>
            </div>
            <div class="mt-6">
                <button v-on:click="newPostMode" class="btn btn-primary btn-lg">New Post</button>
            </div>
        </div>
        <div v-else-if="mode=='NEW_POST'" id="new_post" class="container">
            <h1 class="text-center">Nova objava</h1>
            <form method="post">
                <div class="form-group">
                    <label for="author">Author</label>
                    <input type="text" id="author" name="author" class="form-control" v-model="newPostAuthor" required>
                </div>
                <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text" id="title" name="title" class="form-control" v-model="newPostTitle" required>
                </div>
                <div class="form-group">
                    <label for="content">Content</label>
                    <textarea class="form-control" name="content" id="content" rows="4" v-model="newPostContent" required></textarea>
                </div>
            </form>
            <button v-on:click="savePost" class="btn btn-primary">Save Post</button>
            <button v-on:click="browseMode" class="btn btn-primary">Back</button>
        </div>
        <div v-else id="single_post" class="container">
            <div class="row">
                <div class="col">
                    <h1>{{currentPost.title}}</h1>
                    <h6>{{currentPost.publishDate | dateFormat}}</h6>
                    <h6 class="text-muted">{{currentPost.author}}</h6>
                    <p>{{currentPost.content}}</p>
                    <button v-on:click="browseMode" class="btn btn-primary">Back</button>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <h3>Comments</h3>
                    <h4 v-if="commentsForPost == {}">
                        Nema komentara za ovu objavu.
                    </h4>
                    <div class="card" v-for="c in commentsForPost" >
                        <div class="card-body">
                            <h4 class="card-title">{{c.author}}</h4>
                            <p class="card-text">{{c.content}}</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <h3>New comment</h3>
                        <form method="post">
                            <div class="form-group">
                                <label for="authorComment">Author</label>
                                <input v-model="newCommentAuthor" type="text" required class="form-control" id="authorComment" name="author">
                            </div>
                            <div class="form-group">
                                <label for="comment">Comment</label>
                                <textarea v-model="newCommentContent" class="form-control" rows="4" required id="comment" name="comment"></textarea>
                            </div>
                        </form>
                        <button v-on:click="comment" class="btn btn-primary btn-lg">Comment</button>
                    </div>
            </div>
            </div>
        </div>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.12/dist/vue.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js" integrity="sha512-bZS47S7sPOxkjU/4Bt0zrhEtWx0y0CRkhEp8IckzK+ltifIIE9EMIMTuT/mEzoIMewUINruDBIR/jJnbguonqQ==" crossorigin="anonymous"></script>
    <script src="js/moment.js"></script>
    <script src="js/app.js"></script>

</body>
</html>