<script>
    const dummySubjects = [
        { code: "MK01", name: "Mathematics", description: "N/A", year: "1" },
        { code: "MK02", name: "Physics", description: "N/A", year: "1" },
        { code: "MK03", name: "Physics", description: "N/A", year: "2" },
        { code: "MK04", name: "Biology", description: "N/A", year: "2" },
        { code: "MK05", name: "Mathematics", description: "N/A", year: "3" },
        { code: "MK01", name: "Mathematics", description: "N/A", year: "1" },
        { code: "MK02", name: "Physics", description: "N/A", year: "1" },
        { code: "MK03", name: "Physics", description: "N/A", year: "2" },
        { code: "MK04", name: "Biology", description: "N/A", year: "2" },
        { code: "MK05", name: "Mathematics", description: "N/A", year: "3" },
        { code: "MK01", name: "Mathematics", description: "N/A", year: "1" },
        { code: "MK02", name: "Physics", description: "N/A", year: "1" },
        { code: "MK03", name: "Physics", description: "N/A", year: "2" },
        { code: "MK04", name: "Biology", description: "N/A", year: "2" },
        { code: "MK05", name: "Mathematics", description: "N/A", year: "3" },
        { code: "MK01", name: "Mathematics", description: "N/A", year: "1" },
        { code: "MK02", name: "Physics", description: "N/A", year: "1" },
        { code: "MK03", name: "Physics", description: "N/A", year: "2" },
        { code: "MK04", name: "Biology", description: "N/A", year: "2" },
        { code: "MK05", name: "Mathematics", description: "N/A", year: "3" },
        { code: "MK01", name: "Mathematics", description: "N/A", year: "1" },
        { code: "MK02", name: "Physics", description: "N/A", year: "1" },
        { code: "MK03", name: "Physics", description: "N/A", year: "2" },
        { code: "MK04", name: "Biology", description: "N/A", year: "2" },
        { code: "MK05", name: "Mathematics", description: "N/A", year: "3" },
    ];
</script>

<div class="px-16 flex flex-col justify-center h-full">
    <div class="overflow-y-auto max-h-[80vh] h-full">
        <table class="table">
            <thead class="bg-slate-400 text-white sticky top-0">
                <th>#</th>
                <th>Code</th>
                <th>Name</th>
                <th>Description</th>
                <th>Year</th>
            </thead>
            <tbody>
                {#each dummySubjects as subject, i}
                    <tr class="hover">
                        <th>{i + 1}</th>
                        <td>{subject.code}</td>
                        <td>{subject.name}</td>
                        <td>{subject.description}</td>
                        <td>{subject.year}</td>
                    </tr>
                {/each}
            </tbody>
        </table>
    </div>
</div>
