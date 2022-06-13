<template>
  <p>
    List of past assignment ;
  </p>
  <br>
  <div>
    <vuetable ref="vuetable"
              api-url="/letter-list"
              :fields="fields"
              pagination-path="pagination"
              :sort-order="sortOrder"
              :multi-sort="multiSort"
              :per-page="perPage"
              :append-params="form"
    />
  </div>

</template>

<script>
</script>

<style>

</style>
