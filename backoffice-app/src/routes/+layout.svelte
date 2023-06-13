<script>
    import "../app.css";
    import Navbar from "@components/Navbar.svelte";
</script>

<div class="h-screen flex flex-col">
    <div class="grow-0">
        <Navbar />
    </div>
    <div class="grow">
        <slot />
    </div>
</div>

