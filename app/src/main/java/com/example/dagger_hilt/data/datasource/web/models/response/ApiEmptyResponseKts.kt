package com.example.dagger_hilt.data.datasource.web.models.response

/**
 * separate class for HTTP 204 resposes so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponseKts<T> : ApiResponseKts<T>()