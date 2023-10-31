<p align="center">
  <a href="https://lerna.ai/">
    <img src="https://dev.lerna.ai/img/Lerna.png" width="200" alt="Lerna AI">
  </a>
  <p align="center">
    Lerna Recommendation API
    <br/>
    <a href="https://lerna.ai/">lerna.ai</a>
  </p>
</p>

# Lerna Recommendation API
> Backend Service for Lerna Recommendation

## Table of contents

- [Submit Item](#submit-item)
- [Submit Event](#submit-event)

## Submit Item
### /api/v2/recommendation/item

#### POST
##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| token | query |  | Yes | string |

##### Request body

```json
{
  "itemId": "string",
  "engineId": "string",
  "properties": {
    "category": {},
    "additionalProp1": {},
    "additionalProp2": {},
    "additionalProp3": {}
  },
  "eventTime": "2023-11-06T14:32:51.231Z"
}
```

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

## Submit Event

### /api/v2/recommendation/event

#### POST
##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| token | query |  | Yes | string |

##### Request body

```json
{
  "engineId": "string",
  "event": "string",
  "entityId": "string",
  "targetEntityId": "string",
  "eventTime": "2023-11-06T14:40:02.249Z"
}
```

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |


  <p align="center">
    © All Rights Reserved. Lerna Inc. 2023.
  </p>
