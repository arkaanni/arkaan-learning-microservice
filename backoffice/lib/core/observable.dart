class Observable<T> {
  late T _data;
  final List<Function> _observers = List.empty(growable: true);

  Observable(this._data);

  void observe(Function fun) => _observers.add(fun);

  T get() => _data;

  void publish(Function(T) fun) {
    fun(_data);
    notifyObservers();
  }

  void publishUpdate(T data) {
    _data = data;
    notifyObservers();
  }

  void notifyObservers() {
    for (var observer in _observers) {
      observer.call();
    }
  }
}