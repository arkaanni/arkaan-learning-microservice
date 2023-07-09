<script>
    import { enhance } from "$app/forms";

    export let form;
</script>

{#if form}
    <p>{form?.msg}</p>
{/if}

<form method="post" use:enhance>
    <div class="flex gap-4">
        <div class="flex flex-col gap-8 w-2/3">
            <div class="form-control">
                <label for="#name" class="label">
                    <span class="label-text">Name</span>
                </label>
                <input
                    name="name"
                    id="name"
                    type="text"
                    placeholder="Type here"
                    class="input input-primary w-full"
                />
            </div>
            <div class="form-control">
                <label for="#code" class="label">
                    <span class="label-text">Code</span>
                </label>
                <input
                    name="subjectCode"
                    id="code"
                    type="text"
                    placeholder="Type here"
                    class="input input-primary w-full"
                />
            </div>
            <div class="form-control">
                <label for="#description" class="label">
                    <span class="label-text">Description</span>
                </label>
                <textarea
                    name="description"
                    id="description"
                    type="text"
                    placeholder="Type here"
                    class="textarea textarea-primary textarea-md w-full"
                />
            </div>
            <div class="text-right">
                <a href="/subject" class="btn btn-warning">Cancel</a>
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </div>
        <div class="p-8 w-1/3">
            <p class="text-xl text-center">Add new Subject</p>
        </div>
    </div>
</form>