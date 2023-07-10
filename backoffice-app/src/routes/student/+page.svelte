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
                <th>Student ID</th>
                <th>Name</th>
                <th>Phone</th>
                <th>Semester</th>
            </thead>
            <tbody>
                {#each data.students as student, i}
                    <tr class="hover">
                        <th>{i + 1}</th>
                        <td>{student.student_id}</td>
                        <td>{student.first_name} {student.last_name}</td>
                        <td>{student.phone}</td>
                        <td>{student.semester}</td>
                    </tr>
                {/each}
            </tbody>
        </table>
    </div>
</div>
