export function sortBy(key, isAsc) {
    return (a, b) => {
        const aKey = a[key];
        const bKey = b[key];

        if (typeof aKey === "number" && typeof bKey === "number") {
            return isAsc ? aKey - bKey : bKey - aKey;
        }

        return isAsc ? aKey.localeCompare(bKey) : bKey.localeCompare(aKey);
    };
}