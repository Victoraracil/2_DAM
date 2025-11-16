using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace Video_Club
{
    public class Categoria : INotifyPropertyChanged
    {
        // Atributos
        private int id;
        private string clasificacion = string.Empty;


        [Key]
        public int Id
        {
            get { return id; }
            set { id = value; OnPropertyChanged(); }
        }

        [Required, MaxLength(30)]
        public string Clasificacion
        {
            get { return clasificacion; }
            set { clasificacion = value; OnPropertyChanged(); }
        }

        // Relaciones entre tablas

        // Relación 1:N con Películas
        [InverseProperty("Categoria")]
        public virtual ICollection<Pelicula> Peliculas { get; set; } = new List<Pelicula>();

        // Relación N:M con Clientes
        [InverseProperty("Categorias")]
        public virtual ICollection<Cliente> Clientes { get; set; } = new List<Cliente>();


        // Implementación de la interfaz INotifyPropertyChanged

        public event PropertyChangedEventHandler? PropertyChanged;

        protected void OnPropertyChanged([CallerMemberName] string? propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
