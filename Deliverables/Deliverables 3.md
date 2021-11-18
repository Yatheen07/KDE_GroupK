# Deliverable 3: Technical report

## Approach to Ontology Modelling

### Competency Questions

> Description of Competency Questions that ontology answers

1. What is the GDP of the Country with the most GHG Emission?

2. What is the HDI of country with least GDP?

3. What is the mililtary of country with highest GHG Emission?

4. Waht is the Average GHG Emission of countries in Asia?

5. What is the Population of the country with highest Air pollution death rate?

6. Does corruption offest the GDP and Happiness score?

7. What is the hapiness score of country with highest military?

8. What is the average hapiness score across Europe?

9. Which country has the highest HDI in Asia?

10. Which contient has the highest average HDI?

### Datasets

> Description of datasets selected for application

**Datasets:**

* Dataset 1:
  - Name: Air Pollution
  - Link: https://www.kaggle.com/pavan9065/air-pollution?select=share-deaths-air-pollution.csv
  - Format: .CSV

* Dataset 2:
  - Name: Green House Gas Historical Emission Data
  - Link: https://www.kaggle.com/saurabhshahane/green-house-gas-historical-emission-data
  - Format: .CSV

* Dataset 3:
  - Name: Global Economy, Population data from Macrotrends
  - Link: https://www.kaggle.com/kalilurrahman/global-economy-population-data-from-macrotrends?select=Global+Nations+Economy+-GNI.csv
  - Format: .CSV

* Dataset 4:
  - Name: World Happiness Report
  - Link: https://www.kaggle.com/unsdsn/world-happiness
  - Format: .CSV

* Dataset 5:
  - Name: WorldBank Data on GDP, Population and Military
  - Link: https://www.kaggle.com/greeshmagirish/worldbank-data-on-gdp-population-and-military?select=API_NY.GDP.MKTP.CD_DS2_en_csv_v2_559588.csv
  - Format: .CSV

### Assumptions made

### References to sources used/reused 

> References to sources used/reused e.g. SIOC, FOAF for people

1. rr 
2. geo
3. rdfs
4. geo
5. xsd

### Discussion of your data mapping process

Before proceeding with Uplift, we need to analyse the data and the problem, then abstract the data into different entities based on the structure of the data table, and draw UML diagrams based on the structure and relationships of these entities. We then mapped the UML diagram to describe the nodes, attributes and relationships in the graph database using R2RML.

### Explanation of use of inverse, symmetric and transitive properties

* **Inverse**
  Relationships between nodes are distinguished between active and passive. 'inverse' allows us to access nodes by different relationship directions.

* **symmetric**
  Nodes are related to each other as equals. With 'symmetric', two nodes can find each other using the same relationship.

* **transitive**
  Nodes linked by the 'transitive' relationship and at different levels have the same conditions for inheritance of the relationship, so that a parent node can find its children and children's children by the same relationship.


## Overview of Design

### Description of Application Query Interface

1. Question selection

The section in the red box in the diagram below. You can get the results of the corresponding question by clicking on the question area in the diagram. The results will be displayed in a table and in the knowledge graph respectively.

2. Table display

The section in the red box in the diagram below. The answers to the selected questions will be displayed in the table.

3. Knowledge mapping

The section in the red box in the diagram below. The knowledge map of the selected issue will be displayed in the map area.

### Description of Queries

> The SPARQL in question is included in Deliverable 2.

1. What is the GDP of the Country with the most GHG Emission?



2. What is the HDI of country with least GDP?

3. What is the mililtary of country with highest GHG Emission?

4. Waht is the Average GHG Emission of countries in Asia?

5. What is the Population of the country with highest Air pollution death rate?

6. Does corruption offest the GDP and Happiness score?

7. What is the hapiness score of country with highest military?

8. What is the average hapiness score across Europe?

9. Which country has the highest HDI in Asia?

10. Which contient has the highest average HDI?

## Discussion of challenges faced while ontology modelling or creating queries and mappings

> * Report how you organised your project (e.g. meetings, common google doc ...).
> * Conclusions: Self reflection of group on Strengths/weakness of ontology model, queries & interface

* When building an ontology, you need to consider the structure of the data you currently have, and then create an ontology based on the structure of the data, sometimes even with some processing of the data to get a good structure.

* When creating ontology, it is necessary to sort out the data according to its logical structure, which is always complex.

* Although the basic syntax of SPARQL is not difficult, queries that require the use of UNION queries and aggregations make SPARQL much more complex, and SPARSQL like this is always long and error-prone, and it always takes a lot of time to find the problem when it occurs.

