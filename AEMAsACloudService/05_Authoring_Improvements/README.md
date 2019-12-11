
# Optional Versioning on Page Delete.

### Scenario Roadmap

Versioning pages before deletion allows restoring them after deletion. Page versioning can however have a significant performance impact on deletion. Deleting a single page can take up to 0.5-5 seconds as a result.

Page versioning before deletion is now optional. The default behavior is "yes", to ensure consistency with previous behavior.

#### Lesson Context

In this scenario we will understand how pages are archived in AEM Sites 

### Step 1. Initiate a delete operation in AEM Sites

1. Select (checked selection) sub-page in AEM Sites.

2. Select the ` Delete (Backspace) `

    > ![1.png](./images/1.png)

3. Verify ` Do you want to archive pages before deletion? ` is checked.

    > ![2.png](./images/2.png)

4. Click ` Delete `
5. Repeat Steps 1 - 3 with ` Do you want to archive pages before deletion? ` flag unchecked.

    > ` Archiving pages here is synonymous with creating versions for respective pages. The info panel further explains what can be achieved with "archiving" pages before deletion, namely being able to restore them later.  `



