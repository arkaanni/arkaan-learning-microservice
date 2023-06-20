<form method="post" action="?/addSubject">
    <div class="flex gap-4">
        <div class="flex flex-col gap-8 w-2/3">
            <div class="form-control">
                <label for="#name" class="label">
                    <span class="label-text">Name</span>
                </label>
                <input
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
                    id="description"
                    type="text"
                    placeholder="Type here"
                    class="textarea textarea-primary textarea-md w-full"
                />
            </div>
            <div class="text-right">
                <a href="/subject" class="btn btn-warning">Cancel</a>
                <button class="btn btn-primary">Save</button>
            </div>
        </div>
        <div class="p-8">
            <p class="text-xl">Add new Subject</p>
        </div>
    </div>
</form>