
# One-to-Many Push Rollout Configurations 

### Scenario Roadmap

In Adobe Experience Manager 6.5, out of the box, the Multi Site Manager only allowed 1:1 pull rollouts (for example, the **synchronize** button/option in AEM Live Copy UI). It was not possible to initiate rollouts on the live copy source.
Mult
In order to allow one-to-many push-rollouts, turning a live copy source into a "blueprint" (for many live copies), a "Blueprint Configuration" had to be created (AEM Start/Tools/Sites/Blueprints).

With AEM as a Cloud Service, this would have not been possible as Blueprint Configurations at author runtime are stored under /libs/msm.

In AEM as a Cloud Service, any live copy source is now a "blueprint", meaning the rollout can now be initiated on a live copy source. As soon a live copy is created, the source automatically becomes a "blueprint".

#### Lesson Context

In this scenario, we will create a Livecopy and explore both the Blueprint and the Rollout Mechanism.

##### Step 1: Create a Live Copy

1. Navigate to **Sites> WKND Site > Language Masters > English**
2. Select the **English (en)** site by clicking on its thumbnail.
3. Click **` Create ` > ` Live Copy `**. The Create Live Copy page opens. 
4. Set the destination as ` WKND Site ` by clicking the thumbnail associated with the WKND site. 
5. Click **` Next `**
6. Specify the ` Title ` & ` Name ` in their corresponding fields - these are required.

*NOTE* The Name field can only contain numbers, letters, dashes, commas and underscores. You cannot use special characters or spacing.

7. Leave ` Exclude sub pages ` unchecked
8. Click the ` Rollout Configs ` drop-down arrow and select ` Standard rollout config `.
9. Click **` Create `**. This may take up a minute to create. 

     > ![1.gif](./images/1.gif)

---

##### Step 2: Rollout

1. Select and Edit a [page](http://localhost:4502/editor.html/content/wknd/language-masters/en/magazine.html) from the blueprint site.
2. Open the **` Page Properties `**
3. Goto the **` Blueprint `** Tab
4. Click **` Rollout  ` **![rollout](./images/2.png)
5. Select all Live Copies and Submit

    > ![2.gif](./images/2.gif)

6. Open the Live Copy Page and Verify the rolled out changes.

---

##### Step 3: Live Copy Overview
 
1. Select and edit a page from the WKND Site. With a parent page selected (for example, content/wknd/language-masters/en), in the upper left, click the Page Information icon and select **Open Properties**.
2. Click the **Blueprint** tab.
3. Open the  **` Page Properties `** for the parent page e.g. ` content/wknd/language-masters/en `
4. Click **` Live Copy Overview `**, as shown.

    > ![3.png](./images/3.png)<br>
    > ` Live Copy Overview ` displays the status of Live Copy status for children blueprint pages 
5. Select a child blueprint page and click **` Rollout `**

    > ![4.png](./images/4.png)

6. Select the desired live copy and click **Submit**(blue checkmark in the upper right, as shown). 

    > ![5.png](./images/5.png)


----
