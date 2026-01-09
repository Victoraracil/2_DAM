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
    internal class StationsViewModel : BaseViewModel
    {
        #region atributos
        // Coleccion para almacenar las tiendas de la BBDD
        private List<Station> _listaEstaciones;
        private ObservableCollection<Station> _listaEstacionesVisibles;

        #endregion

        #region Propiedades

        // Tiendas disponibles
        public ObservableCollection<Station> ListaEstacionesVisibles
        {
            get => _listaEstacionesVisibles;
            set => SetProperty(ref _listaEstacionesVisibles, value);
        }

        #endregion

        #region Constructores

        // Delcaramos los comandos
        public ICommand CargarCommand { get; }

        public StationsViewModel()
        {
            // Inicializamos los atributos
            _listaEstaciones = new();
            _listaEstacionesVisibles = new();

            

            // Inicializamos los comandos
            CargarCommand = new RelayCommand(PerformCargarTiendas);
        }

        #endregion

        #region Metodos
        private async void PerformCargarTiendas(object? parameter = null)
        {
            // Abrimos una conexion con la BBDD
            var service = new ServiceStation();

            // Cargamos las tiendas desde la BBDD
            _listaEstaciones = await service.Listar();

            // Cargamos las tiendas en la ObservableCollection
            _listaEstacionesVisibles.Clear();
            foreach (var s in _listaEstaciones)
            {
                _listaEstacionesVisibles.Add(s);
            }
        }

        #endregion
    }
}
