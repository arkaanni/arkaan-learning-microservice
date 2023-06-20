<script>
    export let data
</script>

<div class="flex flex-col justify-center gap-4">
    <div class="flex justify-end">
        <a href="/subject/add" class="btn btn-primary">Add</a>
    </div>
    <div class="overflow-y-auto max-h-[80vh] h-full">
        <table class="table">
            <thead class="bg-primary text-white sticky top-0">
                <th>#</th>
                <th>Code</th>
                <th>Name</th>
                <th>Description</th>
                <th>Year</th>
            </thead>
            <tbody>
                {#each data.subjects as subject, i}
                    <tr class="hover">
                        <th>{i + 1}</th>
                        <td>{subject.subjectCode}</td>
                        <td>{subject.name}</td>
                        <td>{subject.description}</td>
                        <td>{subject.year || 'N/A'}</td>
                    </tr>
                {/each}
            </tbody>
        </table>
    </div>
</div>
