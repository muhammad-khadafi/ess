<template>
  <footer class="footer">
    <div class="container-footer">
      <div class="content has-text-centered">
        <p>
          <strong>ESS System | <a href="https://essmobile.pusilkom.com/">Download Our Mobile App</a> </strong>
        </p>
        <!-- <p>
          <a href="https://essmobile.pusilkom.com/">Download our mobile app!</a>
        </p> -->
        <p>
          Copyright © {{ new Date().getFullYear() }} Pusilkom UI.  All Rights Reserved.
        </p>
      </div>
    </div>
  </footer>
</template>

<style>
    .footer{
      background-color: #FFFF;
      margin-top: 40px;
    }
    .container-footer{
      margin-bottom: -3%;
    }
footer a{
  color:#003EC9!important;
  opacity: .7;  
}

footer a:link {
  text-decoration: none;
}

footer a:visited {
  text-decoration: none;
}

footer a:hover {
  text-decoration: underline;
}

footer a:active {
  text-decoration: underline;
}
</style>
