<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" label-position="left" label-width="0px" class="login-form">
      <h3 class="title">Groza - IOT Platform</h3>
      <p class="login-tip">Log in to See Amazing Groza.</p>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="Username(email)">
          <svg-icon slot="prefix" icon-class="email" class="el-input__icon" style="height: 39px;width: 13px;margin-left: 2px;" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="Password" @keyup.enter.native="handleLogin">
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon" style="height: 39px;width: 13px;margin-left: 2px;" />
        </el-input>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">Remember Password</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button :loading="loading" size="medium" type="success" style="width:100%;" @click.native.prevent="handleLogin">
          <span v-if="!loading">LOGIN</span>
          <span v-else>Login...</span>
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { md5 } from '@/utils/md5'
import Cookies from 'js-cookie'
export default {
  name: 'Login',
  data() {
    return {
      md5Pwd: '',
      loginForm: {
        username: '',
        password: '',
        rememberMe: false
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', message: 'Username can not be empty' }],
        password: [{ required: true, trigger: 'blur', message: 'Password can not be empty' }]
      },
      loading: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.getCookie()
  },
  methods: {
    getCookie() {
      const username = Cookies.get('username')
      const password = Cookies.get('password')
      const rememberMe = Cookies.get('rememberMe')
      // 保存cookie里面的加密后的密码
      this.md5Pwd = password === undefined ? '' : password
      this.loginForm = {
        username: username === undefined ? '' : username,
        password: this.md5Pwd,
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        let pass = this.loginForm.password
        if (pass !== this.md5Pwd) { pass = md5(pass) }
        const user = { username: this.loginForm.username, password: pass, rememberMe: this.loginForm.rememberMe }
        if (valid) {
          this.loading = true
          if (user.rememberMe) {
            Cookies.set('username', user.username, { expires: 1 })
            Cookies.set('password', user.password, { expires: 1 })
            Cookies.set('rememberMe', user.rememberMe, { expires: 1 })
          } else {
            Cookies.remove('username')
            Cookies.remove('password')
            Cookies.remove('rememberMe')
          }
          this.$store.dispatch('Login', user).then(() => {
            this.loading = false
            this.$router.push({ path: this.redirect || '/' })
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  .login {
    display: flex;
    justify-content: center;
    align-items: center;
    background-image:url(https://picabstract-preview-ftn.weiyun.com/ftn_pic_abs_v3/4f8deb9253fdad98a54695a6c5e16fc926dd043401e0f93969278ba4f6278fcd5b82902f17ad68b8b248306a073f2f0c?pictype=scale&from=30113&version=3.3.3.3&uin=917468356&fname=IOT.jpg&size=750);
    height: 100%;
  }
  .title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #ffffff;
  }

  .login-form {
    border-radius: 6px;
    background: #22A7FF;
    width: 30%;
    padding: 25px 25px 5px 25px;
    .el-input {
      height: 30px;
      input {
        height: 30px;
      }
    }
  }
  .login-tip {
    font-size: 15px;
    text-align: center;
    color: #EEEEEE;
  }
</style>
