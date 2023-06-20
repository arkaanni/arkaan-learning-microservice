<script>
    import "../app.css";
</script>

<div class="drawer drawer-open w-full">
    <input id="drawer" type="checkbox" class="drawer-toggle" />
    <div class="drawer-content p-8">
        <slot />
    </div>
    <div class="drawer-side text-base-content">
        <ul class="menu w-80">
            <li><a class="menu-title" href="/">/</a></li>
            <li><a class="menu-title" href="/subject">Subject</a></li>
            <li><a class="menu-title" href="/student">Student</a></li>
            <li><a class="menu-title" href="/courseplan">Course Plan</a></li>
        </ul>
    </div>
</div>
