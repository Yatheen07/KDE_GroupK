## What is the impact of GDP in the wellbeing of the citizens?

> > What is the impact of GDP on the well-being of citizens?
>
> > Thought: The countries with the highest GDP, and the happiness ranking of these countries.

```SPASQL
prefix rr: <http://www.w3.org/ns/r2rml#>
prefix geo: <http://www.opengis.net/ont/geosparql#>
prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
prefix xsd: <http://www.w3.org/2001/XMLSchema#>
prefix oxly: <http://www.example.org/ont#>

select ?country ?gdp ?happiness_rank
where { 
    ?country oxly:hasEconomy ?gdp;
             oxly:hasHappiness_rank ?happiness_rank.
    ?gdp oxly:year "2018".
}
GROUP BY DESC(?gdp)
limit 10
```

## What is the role of air pollution in the well being of citizens?

> > What does air pollution do to the well-being of citizens?
>
> > Thought: Get the top countries with the most polluted air, and then get a ranking of their citizens' well-being.

```SPASQL
prefix rr: <http://www.w3.org/ns/r2rml#>
prefix geo: <http://www.opengis.net/ont/geosparql#>
prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
prefix xsd: <http://www.w3.org/2001/XMLSchema#>
prefix oxly: <http://www.example.org/ont#>

select ?country ?ghg_rank ?airPollution_rank
where { 
    ?country oxly:hasHappiness_rank ?happiness_rank;
             oxly:hasGHG_rank ?ghg_rank .
}
GROUP BY ASC(?airPollution_rank)
limit 10
```

## How does the military expenditure in the percentage of GDP relate to the well being of citizens?

> > What is the relationship between military spending as a percentage of GDP and the well-being of citizens?
>
> > Thought: Get a ranking of the countries with the highest military expenditures, and the well-being of their citizens.

```SPAQL
prefix rr: <http://www.w3.org/ns/r2rml#>
prefix geo: <http://www.opengis.net/ont/geosparql#>
prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
prefix xsd: <http://www.w3.org/2001/XMLSchema#>
prefix oxly: <http://www.example.org/ont#>

select ?country ?militaryExpenditure ?happiness_rank
where { 
    ?country oxly:hasEconomy ?gdp .
    ?gdp oxly:militaryExpenditurePerGdp ?militaryExpenditure .
    ?country oxly:hasHappiness_rank ?happiness_rank .
    FILTER(CONTAINS(str(?gdp), "2018"))
} 
GROUP BY DESC(?gdp)
limit 10
```

## How does the population of a country contribute to the ghg emission of the country?

> > How does the population of a country contribute to its greenhouse gas emissions?
>
> > Thought: Get the top countries in terms of population, and the ranking of their greenhouse gas emissions, or specific greenhouse gas emissions.

## Do higher ghg emissions result in more deaths from air pollution?

> > Do higher greenhouse gas emissions lead to more deaths from air pollution?
>
> > Thought: Get a ranking of the countries with the highest number of deaths from air pollution, and their greenhouse gas emissions, or the amount of greenhouse gas emissions in those countries.


## Do countries with a high GDP growth have high ghg emissions?

```SPAQL
prefix rr: <http://www.w3.org/ns/r2rml#>
prefix geo: <http://www.opengis.net/ont/geosparql#>
prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
prefix xsd: <http://www.w3.org/2001/XMLSchema#>
prefix oxly: <http://www.example.org/ont#>

select ?country ?gdpGrowth ?ghg_rank
where { 
    ?country oxly:hasEconomy ?gdp;
             oxly:hasGHG ?ghg .
    ?ghg oxly:hasGHG_rank .
    FILTER(CONTAINS(str(?gdp), "2018"))
} 
GROUP BY DESC(?gdp)
limit 10
```

## Which are the top 5 countries with high well being index that majorly contribute to ghg emissions?

```SPAQL
prefix rr: <http://www.w3.org/ns/r2rml#>
prefix geo: <http://www.opengis.net/ont/geosparql#>
prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
prefix xsd: <http://www.w3.org/2001/XMLSchema#>
prefix oxly: <http://www.example.org/ont#>

select ?country ?happiness_rank ?ghg_rank
where { 
    ?country oxly:hasHappiness_rank ?happiness_rank.
             oxly:hasGHG_rank ?ghg_rank .
    FILTER(?happiness_rank>30 && ?ghg_rank>30)
} 
GROUP BY DESC(?ghg)
limit 5 
```

## What is the death rate due to outside air pollution in the countries with high GDP/country ranked high in the well being index?


## What is the Land Area of the country with highest ghg emissions?


## Do countries with higher corruption index emit more ghg?

