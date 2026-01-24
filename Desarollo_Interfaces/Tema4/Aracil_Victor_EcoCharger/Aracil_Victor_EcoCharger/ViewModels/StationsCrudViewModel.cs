using Aracil_Victor_EcoCharger.Models;
using Aracil_Victor_EcoCharger.Services;
using Aracil_Victor_EcoCharger.ViewModels.Base;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace Aracil_Victor_EcoCharger.ViewModels
{
    public class StationsCrudViewModel : BaseViewModel
    {
        #region atributos
        // Coleccion para almacenar las estaciones
        private List<Station> _listaEstaciones;
        private ObservableCollection<Station> _listaEstacionesVisibles;

        // Declaramos un objeto de tipo Estacion para la estacion que manipulamos en el CRUD
        private Station _estacion;

        // Declaramos un objeto de tipo Estacion para la seleccionada
        private Station _selected;
        #endregion

        #region propiedades
        // ID de la Estacion con la que trabajamos
        public int Id
        {
            get { return _estacion.Id; }
            set { _estacion.Id = value; OnPropertyChanged(); }
        }

        // NOMBRE de la estacion con la que trabajamos
        public string Name
        {
            get { return _estacion.Name; }
            set { _estacion.Name = value; OnPropertyChanged(); }
        }

        // DIRECCION de la estacion con la que trabajamos
        public string Address
        {
            get { return _estacion.Address; }
            set { _estacion.Address = value; OnPropertyChanged(); }
        }

        // DIRECCION de la estacion con la que trabajamos
        public double Latitude
        {
            get { return _estacion.Latitude; }
            set { _estacion.Latitude = value; OnPropertyChanged(); }
        }

        // DIRECCION de la estacion con la que trabajamos
        public double Longitude
        {
            get { return _estacion.Longitude; }
            set { _estacion.Longitude = value; OnPropertyChanged(); }
        }

        // IsACTIVE de la estacion con la que trabajamos
        public bool IsActive
        {
            get { return _estacion.IsActive; }
            set { _estacion.IsActive = value; OnPropertyChanged(); }
        }

        // Lista de Cargadores de la estacion con la que trabajamos
        public ObservableCollection<Charger> Chargers
        {
            get { return (ObservableCollection<Charger>)_estacion.Chargers; }
            set { _estacion.Chargers = value; OnPropertyChanged(); }
        }

        // Lista de estaciones
        public ObservableCollection<Station> ListaEstacionesVisibles
        {
            get => _listaEstacionesVisibles;
            set => SetProperty(ref _listaEstacionesVisibles, value);
        }

        // Estacion seleccionada
        public Station Selected
        {
            get => _selected;
            set => SetProperty(ref _selected, value);
        }
        #endregion

        #region constructor
        // Declaramos los diferentes ICommands
        public ICommand CargarCommand { get; }
        public ICommand NuevaEstacionCommand { get; }
        public ICommand GuardarEstacionCommand { get; }
        public ICommand BorrarEstacionCommand { get; }
        public ICommand NuevoCargadorCommand { get; }
        public ICommand BorrarCargadorCommand { get; }
        public ICommand SelectedItemChangedCommand { get; }


        public StationsCrudViewModel()
        {
            // Inicializamos los atributos
            _listaEstaciones = new();
            _listaEstacionesVisibles = new();
            _estacion = new Station();
            _selected = new Station();

            // Inicializamos la lista de estaciones visibles
            ListaEstacionesVisibles = new ObservableCollection<Station>();

            // Inicializamos los comandos
            CargarCommand = new RelayCommand(PerformCargarEstaciones);
            NuevaEstacionCommand = new RelayCommand(PerformNuevaEstacion);
            GuardarEstacionCommand = new RelayCommand(PerformGuardarEstacion, CanExecuteBotones);
            BorrarEstacionCommand = new RelayCommand(PerformBorrarEstacion, CanExecuteBotones);
            NuevoCargadorCommand = new RelayCommand(PerformNuevoCargador, CanExecuteBotones);
            BorrarCargadorCommand = new RelayCommand<Charger>(PerformBorrarCargador);
            SelectedItemChangedCommand = new RelayCommand(PerformSelectedItemChanged);
        }

        #endregion

        #region metodos
        // CanExecuteBotones
        private bool CanExecuteBotones(object? parameter)
        {
            //Debug.WriteLine("CanExecuteBotones");

            // Comprbamos que siempre haya un nombre de Estacion
            bool res = !string.IsNullOrWhiteSpace(Name);
            return res;
        }

        // Cargamos las Estaciones
        private async void PerformCargarEstaciones(object? parameter = null)
        {
            // Abrimos una conexion con la BBDD
            var service = new ServiceStation();

            // Cargamos las estaciones desde la BBDD
            _listaEstaciones = await service.Listar();

            // Cargamos las estaciones en la ObservableCollection
            ListaEstacionesVisibles.Clear();
            foreach (var s in _listaEstaciones)
            {
                ListaEstacionesVisibles.Add(s);
            }
        }

        // Guardamos la estacione que estamos editando
        private async void PerformGuardarEstacion(object? parameter = null)
        {

            // Si la estacion no tiene al menos un cargador
            if (Chargers == null) { return; }

            // Nos conectamos a la BBDD
            var service = new ServiceStation();

            // Guardamos los cambios dependiendo de si es una nueva estacion o una modificada
            if (Id == 0)
            {
                // Insertamos la nueva estacion
                var nuevaEstacion = await service.Insertar(_estacion);
                Id = nuevaEstacion.Id;
            }
            else
            {
                var nuevaEstacion = await service.Actualizar(_estacion);
            }

            // Guardamos los cargadores
            var serviceCargadores = new ServiceCharger();

            foreach (var cargador in Chargers)
            {
                cargador.StationId = Id;

                if (cargador.Id == 0)
                {
                    var nuevoCargador = await serviceCargadores.Insertar(cargador);
                }
                else
                {
                    var nuevoCargador = await serviceCargadores.Actualizar(cargador);
                }
            }

            PerformCargarEstaciones();
        }

        // Borramos la estacion que estamos editando
        private async void PerformBorrarEstacion(object? parameter = null)
        {
            // Nos conectamos a la BBDD
            var service = new ServiceStation();
            var serviceCargadores = new ServiceCharger();

            // Eliminamos los cargadores asociadas a la estacion seleccionada
            foreach (var cargador in Chargers)
            {
                var cargadorBorrado = await serviceCargadores.Borrar(cargador.Id);
            }

            // Eliminamos la estacion seleccionada
            var estacionBorrada = await service.Borrar(Id);

            // Recargamos la lista
            PerformCargarEstaciones();
            PerformLimpiarEstaciones();
        }


        // Añadimos una nueva estacion

        private void PerformNuevaEstacion(object? parameter = null)
        {
            // Inicializamos una nueva estacion
            Id = 0;
            Name = string.Empty;
            Address = string.Empty;
            Latitude = 0;
            Longitude = 0;
            IsActive = true;
            Chargers = new ObservableCollection<Charger>();

            // Añadimos un cargador por defecto a la estacion
            PerformNuevoCargador();
        }


        // Añadimos un nuevo cargador
        private void PerformNuevoCargador(object? parameter = null)
        {
            Chargers.Add(new Charger
            {
                Type = 0,
                IsOccupied = false
            });
        }


        // Borramos un cargador de la lista
        private async void PerformBorrarCargador(Charger charger)
        {
            if (charger == null) return;
            if (Chargers.Count <= 1) { return; }

            Chargers.Remove(charger);

            if (charger.Id != 0)
            {
                var serviceCargadores = new ServiceCharger();
                var cargadorBorrada = await serviceCargadores.Borrar(charger.Id);
            }
        }

        // Manejador de cambio de Item en la lista de estaciones
        private void PerformSelectedItemChanged(object? parameter = null)
        {
            // Comprobamos que haya un objeto seleccionado
            if (parameter != null)
            {
                // Copiamos los datos recibidos desde el SelectedItem de la interfaz a los parametros locales que tenemos en el ViewModel
                Id = Selected.Id;
                Name = Selected.Name;
                Address = Selected.Address;
                IsActive = Selected.IsActive;
                Latitude = Selected.Latitude;
                Longitude = Selected.Longitude;
                Chargers = (ObservableCollection<Charger>)Selected.Chargers;
            }
        }

        // Limpiador de Interfaz
        private void PerformLimpiarEstaciones(object? parameter = null)
        {
            Id = 0;
            Name = string.Empty;
            Address = string.Empty;
            Latitude = 0;
            Longitude = 0;
            IsActive = true;
            Chargers = new ObservableCollection<Charger>();
        }

        #endregion
    }
}
