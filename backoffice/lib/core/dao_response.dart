enum Status {success, failed}

class DaoResponse<T> {
  final T? data;
  final Status status;
  final String? message;
  final String? error;

  DaoResponse(this.data, this.status, this.message, this.error);
}